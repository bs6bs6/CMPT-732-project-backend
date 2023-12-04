package com.bs6.election.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmitterService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(String requestId, SseEmitter emitter) {
        emitters.put(requestId, emitter);
    }

    public SseEmitter getEmitter(String requestId) {
        return emitters.get(requestId);
    }

    public void removeEmitter(String requestId) {
        emitters.remove(requestId);
    }
}
