package com.gto.aws.service;
import static com.basho.riak.client.convert.KeyUtil.getKey;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
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
import com.esotericsoftware.kryo.serialize.DateSerializer;
import com.gto.aws.model.DataPoint;
import com.gto.aws.model.Ec2CpuUtilizationInstance;
import com.gto.aws.model.Ec2CpuUtilizationJob;

public class Ec2CpuUtilizationInstanceConvertor implements Converter<Ec2CpuUtilizationInstance>{
	private String bucket;

	private final UsermetaConverter<Ec2CpuUtilizationInstance> usermetaConverter;
	private final RiakIndexConverter<Ec2CpuUtilizationInstance> riakIndexConverter;
	private final RiakLinksConverter<Ec2CpuUtilizationInstance> riakLinksConverter;
    
    public Ec2CpuUtilizationInstanceConvertor(String bucket)
    {
        this.bucket = bucket;
        this.usermetaConverter = new UsermetaConverter<Ec2CpuUtilizationInstance>();
        this.riakIndexConverter = new RiakIndexConverter<Ec2CpuUtilizationInstance>();
        this.riakLinksConverter = new RiakLinksConverter<Ec2CpuUtilizationInstance>();
     
    }
    
    
    /* (non-Javadoc)
	 * @see com.gto.aws.service.IGenericCryoConvertor#fromDomain(T, com.basho.riak.client.cap.VClock)
	 */

	public IRiakObject fromDomain(Ec2CpuUtilizationInstance domainObject, VClock vclock) throws ConversionException
    {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("from DOmain");
        String key = getKey(domainObject);
        
        if (key == null)
        {
            throw new NoKeySpecifedException(domainObject);
        }
        
        Kryo kryo = new Kryo();

        kryo.register(java.util.Date.class,new DateSerializer());
        kryo.register(DataPoint.class);
        kryo.register(com.gto.aws.model.Data.class);
        kryo.register(com.gto.aws.model.Ec2CpuUtilizationInstance.class);
        kryo.register(java.util.ArrayList.class,new CollectionSerializer(kryo));
        
        ObjectBuffer ob = new ObjectBuffer(kryo,9999999);
        byte[] value = ob.writeObject(domainObject);
        
        Map<String, String> usermetaData = usermetaConverter.getUsermetaData( domainObject);
        RiakIndexes indexes = riakIndexConverter.getIndexes(domainObject);
        Collection<RiakLink> links = riakLinksConverter.getLinks( domainObject);
        
        return RiakObjectBuilder.newBuilder(bucket, key)
            .withValue(value)
            .withVClock(vclock)
            .withUsermeta(usermetaData)
            .withIndexes(indexes)
            .withLinks(links)
            .withContentType(Constants.CTYPE_JSON_UTF8)
            .build();
        
        
    }

    /* (non-Javadoc)
	 * @see com.gto.aws.service.IGenericCryoConvertor#toDomain(com.basho.riak.client.IRiakObject)
	 */

	public Ec2CpuUtilizationInstance toDomain(IRiakObject riakObject) throws ConversionException
    {
        
        if (riakObject == null)
            return null;
      
        Kryo kryo = new Kryo();

     System.out.println("inside to doamin");
     kryo.register(java.util.Date.class,new DateSerializer());
     kryo.register(DataPoint.class);
     kryo.register(com.gto.aws.model.Data.class);
     kryo.register(com.gto.aws.model.Ec2CpuUtilizationInstance.class);
 
        kryo.register(java.util.ArrayList.class,new CollectionSerializer(kryo));
        ObjectBuffer ob = new ObjectBuffer(kryo,9999999);
        
        Ec2CpuUtilizationInstance domainObject = ob.readObject(riakObject.getValue(), Ec2CpuUtilizationInstance.class);
        System.out.println(domainObject.getClass());
         usermetaConverter.populateUsermeta(riakObject.getMeta(), domainObject);
         riakIndexConverter.populateIndexes(new RiakIndexes(riakObject.allBinIndexes(), riakObject.allIntIndexesV2()),
                                               domainObject);
         riakLinksConverter.populateLinks(riakObject.getLinks(), domainObject);
        System.out.println(riakObject.allBinIndexes().size());
        
        return domainObject;
        
    }


}