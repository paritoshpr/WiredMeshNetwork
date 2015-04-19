package WiredMeshNetworkSim;


import GenCol.*;

public class packetEntity extends entity{
  //protected double processingTime;
  //protected double price;
  //protected int priority;
  protected int src;
  protected int dest;
  
  public packetEntity(){
	  this("packet", 0,0);
  }
  
  public packetEntity(String name, int _src, int _dest){
	  super(name);
	  //processingTime = _procTime;
	  //price = _price;
	  src = _src;
	  dest = _dest;
	  
  }
	
 /* public double getProcessingTime(){
	  return processingTime;
  }
  
  public double getPrice(){
	  return price;
  }*/
  
  public int getSrc(){
	  return src;
  }
  public int getDest(){
	  return dest;
  }
  /*public String toString(){
	  return name+"_"+(double)((int)(processingTime*100))/100;
	  //return name+"_"+((double)((int)(processingTime*100)))/100;
  }*/
		
}
