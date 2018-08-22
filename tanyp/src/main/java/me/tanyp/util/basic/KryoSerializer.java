package me.tanyp.util.basic;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

// kryo 序列化
public class KryoSerializer {
	
	private static KryoPool.Builder builder = new KryoPool.Builder(new KryoFactory() {
		@Override
		public Kryo create() {
			return new Kryo();
		}
	});
	private static KryoPool kryoPool = builder.build();
	
	public static <T> byte[] serialize(T t) {
		Output output = null;
		ByteArrayOutputStream baos = null;
		Kryo kryo = kryoPool.borrow();
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			output = new Output(baos);
			kryo.writeObject(output, t);
			output.flush();
			return baos.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("序列化时出现异常:" + t, e);
		} finally {
			kryoPool.release(kryo);
			if (output != null) {
				output.close();
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static <T> T deserialize(Class<T> clazz, byte[] data) {
		Input input = null;
		ByteArrayInputStream bais = null;
		Kryo kryo = kryoPool.borrow();
		try {
			// 反序列化
			bais = new ByteArrayInputStream(data);
			input = new Input(bais);
			return (T) kryo.readObject(input, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("反序列化时出现异常", e);
		} finally {
			kryoPool.release(kryo);
			if (input != null) {
				input.close();
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
