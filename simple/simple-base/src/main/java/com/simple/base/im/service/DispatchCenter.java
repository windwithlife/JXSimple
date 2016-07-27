package com.simple.base.im.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.base.im.server.FixedThreadPoolExecutor;
import com.simple.base.im.server.Request;
import com.simple.base.im.server.Response;
//import com.simple.base.im.server.RequestCachePool.MessageWorker;
import com.simple.core.util.SerializationTools;

import java.lang.reflect.*;

public class DispatchCenter {
	private static DispatchCenter instance = new DispatchCenter();
	//private Map<String, IServiceHandler> handlerMap = new ConcurrentHashMap<String, IServiceHandler>();
	private Map<String, MappingActionItem> RequestMap = new ConcurrentHashMap<String, MappingActionItem>(); 
	//private Map<String, Class<?>> RequestParamTypeMap = new ConcurrentHashMap<String, Class<?>>(); 
	private ApplicationContext context = null;
	private Executor messageExecutor;
	private class MappingActionItem{
		public Object handler;
		public Method action;
		public Class<?> returnType;
		public Class<?> inputType;
		public Class<?> requestContextType;
		public Class<?> outputType;
		
	}
	public static DispatchCenter getInstance(){
		return instance;
	}
	
	
	public void registerService(){
		//this.context =SpringContext.createFileContext(null);
		Map<String, Object> map = context.getBeansWithAnnotation(NIOService.class);
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()){
			Entry<String, Object> element = it.next();
			Object handler = element.getValue();
			//Method[] methods = handler.getClass().getMethods();
			HandlerMapping handlerMapping = handler.getClass().getAnnotation(HandlerMapping.class);
			Method[] methods = handler.getClass().getDeclaredMethods();
			for (Method method : methods){
				HandlerMapping methodMapping = method.getAnnotation(HandlerMapping.class);
				Class<?> returnClass = method.getReturnType();
				Class<?>[] cls = method.getParameterTypes();
				MappingActionItem item = new MappingActionItem();
				item.handler = handler;
				item.action  = method;
				item.returnType = returnClass;
				if (cls.length > 0){
					item.inputType  = cls[0];
					if (cls.length > 1){
						item.requestContextType = cls[1];	
					}
				}
				
				//item.outputType = cls[1];
				String requestPathKey  = "";
				if (handlerMapping != null){
					requestPathKey  = "/" + handlerMapping.path();
				}
				if ((methodMapping != null) && (!methodMapping.path().equalsIgnoreCase(""))){
					requestPathKey = requestPathKey + "/" + methodMapping.path();
				}else if (handlerMapping == null){
					requestPathKey = "/";
				}
				
				String requestIdKey  = methodMapping.id();
				String requestDefaultKey  = "/" + handler.getClass().getSimpleName() +"/" + method.getName();
				String requestKey = requestDefaultKey;
				
				SerializationTools.getInstance().registerMapping(requestPathKey, item.inputType);
				SerializationTools.getInstance().registerMapping(requestIdKey, item.inputType);
				
				RequestMap.put(requestPathKey, item);
				RequestMap.put(requestIdKey, item);
				
				System.out.println("[HandlerName:]" + handler.getClass().getSimpleName() + "\r\n[MethodName:]" 
				+ method.getName() + "\r\n[returnName:]" + returnClass + "\r\n[input-param:]" + item.inputType );
				
	            System.out.println("class key:" + requestKey);
	            System.out.println("path key:" + requestPathKey);
	            System.out.println("id key:" + requestIdKey);
	           
			}
			
			//this.registerServiceHandler(handler.getCommand(), handler);
		}
	}
	
	public void executeRequest(Request request){
		MessageWorker messageWorker = new MessageWorker(request);
		this.messageExecutor.execute(messageWorker);
		request = null;
		//this.handleRequest(request);
	}
	
	private void handleRequest(Request request){
		try{
			
			String requestCommand = request.getCommand();
			MappingActionItem item = RequestMap.get(requestCommand);
			//Object obj = item.inputType.newInstance();
			Object result = null;
			if (item.requestContextType != null){
				result = item.action.invoke(item.handler, request.getInput(), request);
			}else{
				result = item.action.invoke(item.handler, request.getInput());
			}
			
			Response response = new Response(request);
			
			if (!response.doResponse(result)){
				System.out.println("failed to write response to client");
			}
			response = null;
			request = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setApplicationSpringContext(ApplicationContext appContext){
		this.context = appContext;
	}
	public void LoadServiceCenter(){
		if (null == context){
			this.context = SpringContext.getApplicationContext();
		}
		
		registerService();
	}
	public DispatchCenter(){
		this.messageExecutor = new FixedThreadPoolExecutor(5,80,300);
		
		
	}
	
	
	
	private final class MessageWorker implements Runnable {
		private Request request;
		//private Response response;
		//private IServiceHandler service;

		public MessageWorker(Request req) {
			this.request = req;
		}

		public void run() {
			try {
				long start = System.currentTimeMillis();
				// logger.info("协议  "+messageId + "处理开始!");
				try {

					//service.execute(request, response);
					DispatchCenter.getInstance().handleRequest(request);
				} catch (Exception e) {
					// logger.error(e.getMessage());
				}finally{
					
				}
				// logger.info("协议  "+messageId + "处理完成!");
				long end = System.currentTimeMillis();
				long diff = (end - start);
				
				if (diff >= 200) {
					// logger.warn(messageId + "逻辑处理时间过长！处理时间(s)：" + diff);
					System.out.println(request.getCommand()
							+ "逻辑处理时间过长！处理时间(毫秒)：" + diff);
				}else {
					System.out.println("Request[ID:" + request.getCommand()+ "] is done in- " + diff + " -millionare seconds!" );
				}

			} finally {
				// messageQueue.setRunning(false);
			}
		}

	}// end of private class worker.
}
