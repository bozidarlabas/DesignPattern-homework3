/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz.zadaca.registry;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bozidar
 */
public class Registry<ServiceType> {
    
    private final Map<String, ServiceType> services  = new HashMap<>();

    public void register(String name, ServiceType service){
        services.put(name, service);
    }
    
    public ServiceType resolve(String name){
         return (ServiceType)services.get(name);
    }
}
