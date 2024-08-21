package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner dp=new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,dp);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            orderToPartnerMap.put(orderId,partnerId);
            DeliveryPartner p=partnerMap.get(partnerId);
            p.setNumberOfOrders(p.getNumberOfOrders()+1);
            HashSet<String> hash = new HashSet<>();
            if(partnerToOrderMap.containsKey(partnerId)){
                hash = partnerToOrderMap.get(partnerId);
            }
            hash.add(orderId);
            partnerToOrderMap.put(partnerId,hash);
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        return partnerToOrderMap.get(partnerId).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        List<String> ret = new ArrayList<>();
        HashSet<String> h = partnerToOrderMap.get(partnerId);
        for(String s: h){
            ret.add(s);
        }
        return ret;
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String> lst= new ArrayList<>();
        for(Map.Entry<String, Order> e: orderMap.entrySet()){
            lst.add(e.getKey());
        }
        return lst;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        partnerMap.remove(partnerId);
        HashSet<String> set = partnerToOrderMap.get(partnerId);
        for(String s: set){
            orderToPartnerMap.remove(s);
        }
        partnerToOrderMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        orderMap.remove(orderId);
        String pId = orderToPartnerMap.get(orderId);
        if(!pId.isBlank()){
            partnerToOrderMap.remove(pId);
            orderToPartnerMap.remove(orderId);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int totalOrders=orderMap.size();
        int assignedOrders = orderToPartnerMap.size();
        return totalOrders - assignedOrders;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int hr = Integer.parseInt(timeString.substring(0,2));
        int min = Integer.parseInt(timeString.substring(3));
        int time = hr*60+min;
        HashSet<String> set = partnerToOrderMap.get(partnerId);
        int count = 0;
        for(String s: set){
            Order o = orderMap.get(s);
            if(o.getDeliveryTime() > time)count++;
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        HashSet<String> set = partnerToOrderMap.get(partnerId);
        int time = 0;
        for(String s: set){
            Order o= orderMap.get(s);
            if(o.getDeliveryTime() > time)time= o.getDeliveryTime();
        }
        String strTime = String.valueOf(time/60) +":"+ String.valueOf(time%60 );
        return strTime ;
    }
}