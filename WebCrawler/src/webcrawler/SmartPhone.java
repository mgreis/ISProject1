/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

/**
 *
 * @author Mário
 */
public class SmartPhone {
    
    private String brand="Brand not Listed";
    private String model="Model not Listed";
    private String processor="Processor not Listed";
    private String screenTech="Screen Technology not Listed";
    private String screenSize="Screen Size not Listed";
    private String otherInfo="Other information not Listed";
    private String bestPrice="best price not Listed";
    private String ourPrice="our price not Listed";
    private String address="web address not Listed";
    
    public SmartPhone(){
        
    }
   public void printPhone(){
       System.out.println("Start");
       System.out.println(this.getBestPrice());
       System.out.println(this.getBrand());
       System.out.println(this.getModel());
       System.out.println(this.getOtherInfo());
       System.out.println(this.getOurPrice());
       System.out.println(this.getProcessor());
       System.out.println(this.getScreenSize());
       System.out.println(this.getScreenTech());
       System.out.println(this.getAddress());
       System.out.println("Stop");
   }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the processor
     */
    public String getProcessor() {
        return processor;
    }

    /**
     * @param processor the processor to set
     */
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /**
     * @return the screenTech
     */
    public String getScreenTech() {
        return screenTech;
    }

    /**
     * @param screenTech the screenTech to set
     */
    public void setScreenTech(String screenTech) {
        this.screenTech = screenTech;
    }

    /**
     * @return the screenSize
     */
    public String getScreenSize() {
        return screenSize;
    }

    /**
     * @param screenSize the screenSize to set
     */
    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * @return the camera
     */
    public String getOtherInfo() {
        return otherInfo;
    }

    /**
     * @param camera the camera to set
     */
    public void setOtherInfo(String OtherInfo) {
        this.otherInfo = otherInfo;
    }

    /**
     * @return the bestPrice
     */
    public String getBestPrice() {
        return bestPrice;
    }

    /**
     * @param bestPrice the bestPrice to set
     */
    public void setBestPrice(String bestPrice) {
        this.bestPrice = bestPrice;
    }

    /**
     * @return the ourPrice
     */
    public String getOurPrice() {
        return ourPrice;
    }

    /**
     * @param ourPrice the ourPrice to set
     */
    public void setOurPrice(String ourPrice) {
        this.ourPrice = ourPrice;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
