package me.toolkit.java.util.cache;

import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangdi5 on 2015/8/4.
 */
public class TimerCache {


    private final static Logger logger = LoggerFactory.getLogger(TimerCache.class);

     public static LoadingCache<String,String> cache = null;



    private void init(){

        cache= CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).initialCapacity(10).maximumSize(50)
                .removalListener(new RemovalListener<Object, Object>() {

                    @Override
                    public void onRemoval(RemovalNotification<Object, Object> notification) {
                        logger.debug("缓存失效"+notification.getKey() + " was removed, cause is " + notification.getCause());
                    }

                }).build(new CacheLoader<String, String>() {
            @Override
            public String load(String s) throws Exception {


                return null;
            }
        });
    }
 }
