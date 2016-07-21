package com.hxp.common.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

public class ObjectBytesExchange {

	private static Log log = LogFactory.getLog(ObjectBytesExchange.class);

	  public static byte[] toByteArray(Object obj)
	  {
	    byte[] bytes = null;
	    ObjectOutputStream oos = null;
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
	      oos = new ObjectOutputStream(bos);
	      oos.writeObject(obj);
	      oos.flush();
	      bytes = bos.toByteArray();
	      oos.close();
	      oos = null;
	      bos.close();
	      bos = null;

	      if (oos != null) {
	        try {
	          oos.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toByteArray() >>> oos.close() throw a IOException.");
	        }
	      }
	      if (null != bos)
	        try {
	          bos.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toByteArray() >>> bos.close() throw a IOException.");
	        }
	    }
	    catch (IOException e)
	    {
	      log.error("ObjectBytesExchange.toByteArray() throw a IOException.");

	      if (oos != null) {
	        try {
	          oos.close();
	        } catch (IOException e1) {
	          log.error("ObjectBytesExchange.toByteArray() >>> oos.close() throw a IOException.");
	        }
	      }
	      if (null != bos)
	        try {
	          bos.close();
	        } catch (IOException e1) {
	          log.error("ObjectBytesExchange.toByteArray() >>> bos.close() throw a IOException.");
	        }
	    }
	    finally
	    {
	      if (oos != null) {
	        try {
	          oos.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toByteArray() >>> oos.close() throw a IOException.");
	        }
	      }
	      if (null != bos) {
	        try {
	          bos.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toByteArray() >>> bos.close() throw a IOException.");
	        }
	      }
	    }
	    return bytes;
	  }

	  public static Object toObject(byte[] bytes)
	  {
	    Object obj = null;
	    ObjectInputStream ois = null;
	    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
	    try {
	      ois = new ObjectInputStream(bis);
	      obj = ois.readObject();
	      ois.close();
	      ois = null;
	      bis.close();
	      bis = null;

	      if (ois != null) {
	        try {
	          ois.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toObject() >>> ois.close() throw a IOException.");
	        }
	      }
	      if (null != bis)
	        try {
	          bis.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toObject() >>> bis.close() throw a IOException.");
	        }
	    }
	    catch (IOException e)
	    {
	      log.error("ObjectBytesExchange.toObject() throw a IOException.");

	      if (ois != null) {
	        try {
	          ois.close();
	        } catch (IOException e1) {
	          log.error("ObjectBytesExchange.toObject() >>> ois.close() throw a IOException.");
	        }
	      }
	      if (null != bis)
	        try {
	          bis.close();
	        } catch (IOException e1) {
	          log.error("ObjectBytesExchange.toObject() >>> bis.close() throw a IOException.");
	        }
	    }
	    catch (ClassNotFoundException e)
	    {
	      log.error("ObjectBytesExchange.toObject() throw a ClassNotFoundException.");

	      if (ois != null) {
	        try {
	          ois.close();
	        } catch (IOException e1) {
	          log.error("ObjectBytesExchange.toObject() >>> ois.close() throw a IOException.");
	        }
	      }
	      if (null != bis)
	        try {
	          bis.close();
	        } catch (IOException e1) {
	          log.error("ObjectBytesExchange.toObject() >>> bis.close() throw a IOException.");
	        }
	    }
	    finally
	    {
	      if (ois != null) {
	        try {
	          ois.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toObject() >>> ois.close() throw a IOException.");
	        }
	      }
	      if (null != bis) {
	        try {
	          bis.close();
	        } catch (IOException e) {
	          log.error("ObjectBytesExchange.toObject() >>> bis.close() throw a IOException.");
	        }
	      }
	    }
	    return obj;
	  }
}
