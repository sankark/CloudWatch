package com.gto.aws.service;
import static com.basho.riak.client.convert.KeyUtil.getKey;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

import com.basho.riak.client.IRiakObject;
import com.basho.riak.client.RiakLink;
import com.basho.riak.client.builders.RiakObjectBuilder;
import com.basho.riak.client.cap.VClock;
import com.basho.riak.client.convert.ConversionException;
import com.basho.riak.client.convert.Converter;
import com.basho.riak.client.convert.NoKeySpecifedException;
import com.basho.riak.client.convert.RiakIndexConverter;
import com.basho.riak.client.convert.RiakLinksConverter;
import com.basho.riak.client.convert.UsermetaConverter;
import com.basho.riak.client.http.util.Constants;
import com.basho.riak.client.query.indexes.RiakIndexes;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.serialize.CollectionSerializer;
import com.gto.aws.model.Ec2CpuUtilizationInstance;

public abstract class GenericCryoConvertor<T> implements IGenericCryoConvertor<T>
{

	protected String bucket;
    protected Class<T> clazz;
    protected final UsermetaConverter<T> usermetaConverter;
    protected final RiakIndexConverter<T> riakIndexConverter;
    protected final RiakLinksConverter<T> riakLinksConverter;
    
    public GenericCryoConvertor(String bucket)
    {
        this.bucket = bucket;
        this.usermetaConverter = new UsermetaConverter<T>();
        this.riakIndexConverter = new RiakIndexConverter<T>();
        this.riakLinksConverter = new RiakLinksConverter<T>();
        clazz = ((Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    
    
    /* (non-Javadoc)
	 * @see com.gto.aws.service.IGenericCryoConvertor#fromDomain(T, com.basho.riak.client.cap.VClock)
	 */
    @Override
	public IRiakObject fromDomain(T domainObject, VClock vclock) throws ConversionException
    {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("from DOmain");
        String key = getKey(domainObject);
        
        if (key == null)
        {
            throw new NoKeySpecifedException(domainObject);
        }
        
        Kryo kryo = new Kryo();
        kryo.register(clazz);
        kryo.register(java.util.ArrayList.class);
        kryo.register(java.util.ArrayList.class,new CollectionSerializer(kryo));
        
        ObjectBuffer ob = new ObjectBuffer(kryo);
        byte[] value = ob.writeObject(domainObject);
        
        Map<String, String> usermetaData = usermetaConverter.getUsermetaData(domainObject);
        RiakIndexes indexes = riakIndexConverter.getIndexes(domainObject);
        Collection<RiakLink> links = riakLinksConverter.getLinks(domainObject);
        
        return RiakObjectBuilder.newBuilder(bucket, key)
            .withValue(value)
            .withVClock(vclock)
            .withUsermeta(usermetaData)
            .withIndexes(indexes)
            .withLinks(links)
            .withContentType(Constants.CTYPE_OCTET_STREAM)
            .build();
        
        
    }

    /* (non-Javadoc)
	 * @see com.gto.aws.service.IGenericCryoConvertor#toDomain(com.basho.riak.client.IRiakObject)
	 */
    @Override
	public T toDomain(IRiakObject riakObject) throws ConversionException
    {
        
        if (riakObject == null)
            return null;
        System.out.println(clazz);
        Kryo kryo = new Kryo();
        kryo.register(clazz);
     System.out.println("inside to doamin");
        kryo.register(java.util.ArrayList.class,new CollectionSerializer(kryo));
        ObjectBuffer ob = new ObjectBuffer(kryo);
        
        T domainObject = ob.readObject(riakObject.getValue(), clazz);
        System.out.println(domainObject.getClass());
        domainObject = usermetaConverter.populateUsermeta(riakObject.getMeta(), domainObject);
        domainObject = riakIndexConverter.populateIndexes(new RiakIndexes(riakObject.allBinIndexes(), riakObject.allIntIndexesV2()),
                                               domainObject);
        domainObject = riakLinksConverter.populateLinks(riakObject.getLinks(), domainObject);
        System.out.println(riakObject.allBinIndexes().size());
        
        return domainObject;
        
    }
}