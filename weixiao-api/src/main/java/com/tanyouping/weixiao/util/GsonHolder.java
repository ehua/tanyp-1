package com.tanyouping.weixiao.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.tanyouping.weixiao.exception.SystemException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GsonHolder {
	
	private static final GsonHolder HOLDER = new GsonHolder();

	private final GsonBuilder BUILDER = new GsonBuilder();
	
	private Gson gson;
	
	{
		BUILDER.registerTypeAdapter(BigDecimal.class, new TypeAdapter<BigDecimal>() {
			@Override
			public void write(JsonWriter out, BigDecimal value) throws IOException {
				if(value == null){
					out.value("");
				} else {
					out.value(value.doubleValue());
				}
			}
			@Override
			public BigDecimal read(JsonReader in) throws IOException {
				String val = in.nextString();
				if (StringUtils.isEmpty(val)) {
					return null;
				} else {
					return new BigDecimal(val);
				}
			}
		});
		BUILDER.registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {
            @Override
            public void write(JsonWriter out, Integer value) throws IOException {
            	if(value == null){
            		out.value("");
            	} else {
            		out.value(value);
            	}
            }
            @Override
            public Integer read(JsonReader in) throws IOException {
                    String val = in.nextString();
                    if (StringUtils.isEmpty(val)) {
                    	return null;
                    } else {
                    	return Integer.parseInt(val);
                    }
            }
        });
		BUILDER.registerTypeAdapter(Long.class, new TypeAdapter<Long>() {
            @Override
            public void write(JsonWriter out, Long value) throws IOException {
            	if(value == null){
            		out.value("");
            	} else {
            		out.value(value);
            	}
            }
            @Override
            public Long read(JsonReader in) throws IOException {
                    String val = in.nextString();
                    if (StringUtils.isEmpty(val)) {
                    	return null;
                    } else {
                    	return Long.parseLong(val);
                    }
            }
        });
		BUILDER.registerTypeAdapter(Date.class, new TypeAdapter<Date>() {
			@Override
			public void write(JsonWriter out, Date value) throws IOException {
				if (value == null) {
					out.value("");
				} else {
					out.value(value.getTime());
				}
			}

			@Override
			public Date read(JsonReader in) throws IOException {
				String val = null;
				try {
					val = in.nextString();
				} catch (IllegalStateException e){
					e.printStackTrace();
					return null;
				}
                if (StringUtils.isEmpty(val)) {
                	return null;
                } else {
                	try {
                		long value = Long.parseLong(val);
                		return new Date(value);
                	} catch (NumberFormatException e) {}
                	//解析失败则继续
                	Date date = DateUtils.formatDate(val);
                	if (date == null) {
//                		throw new SystemException("解析失败，请检查日期格式：" + val);
                	}
                	return date;
                }
			}
		});
		BUILDER.registerTypeAdapter(java.sql.Date.class, new TypeAdapter<java.sql.Date>() {
			@Override
			public void write(JsonWriter out, java.sql.Date value) throws IOException {
				if (value == null) {
					out.value("");
				} else {
					out.value(value.getTime());
				}
			}

			@Override
			public java.sql.Date read(JsonReader in) throws IOException {
				String val = null;
				try {
					val = in.nextString();
				} catch (IllegalStateException e){
					e.printStackTrace();
					return null;
				}
                if (StringUtils.isEmpty(val)) {
                	return null;
                } else {
                	try {
                		long value = Long.parseLong(val);
                		return new java.sql.Date(value);
                	} catch (NumberFormatException e) {}
                	//解析失败则继续
                	Date date = DateUtils.formatDate(val);
                	if (date == null) {
                		throw new SystemException("解析失败，请检查日期格式：" + val);
                	}
                	return new java.sql.Date(date.getTime());
                }
			}
		});
		BUILDER.registerTypeAdapter(new TypeToken<List<Date>>() {}.getType(), new TypeAdapter<List<Date>>() {
			@Override
			public void write(JsonWriter out, List<Date> value) throws IOException {
				if (value == null || value.size() == 0) {
					out.beginArray();
					out.endArray();
				} else {
					out.beginArray();
					int size = value.size();
					for (int i = 0; i < size; i++) {
						out.value(value.get(i).getTime());
					}
					out.endArray();
				}
			}

			@Override
			public List<Date> read(JsonReader in) throws IOException {
                	List<Date> result = new ArrayList<>();
                	in.beginArray();
                	while (in.hasNext()) {
                		String value = in.nextString();
                		if (StringUtils.isEmpty(value)) {
                			continue;
                		}
                		Date date = null;
                		try {
                			date = new Date(Long.parseLong(value));
                		} catch (NumberFormatException e) {}
                		//解析失败则继续
                		if ( date == null) {
                			date = DateUtils.formatDate(value);
                		}
                		if (date == null) {
                			throw new SystemException("解析失败，请检查日期格式：" + value);
                		}
                		result.add(date);
					}
                	in.endArray();
                	return result;
			}
		});
	}
	
	public static GsonHolder get(){
		return HOLDER;
	}
	
	public GsonBuilder getBuilder(){
		return BUILDER;
	}
	
	public Gson getGson(){
		if (gson == null) {
			gson = BUILDER.create();
		}
		return gson;
	}

}
