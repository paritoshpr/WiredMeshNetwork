package WiredMeshNetworkSim;
/*      Copyright 2002 Arizona Board of regents on behalf of
 *                  The University of Arizona
 *                     All Rights Reserved
 *         (USE & RESTRICTION - Please read COPYRIGHT file)
 *
 *  Version    : DEVSJAVA 2.7
 *  Date       : 08-15-02
 */




import simView.*;

import java.lang.*;
import java.util.Random;

import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import util.*;
import statistics.*;

public class WiredNode extends ViewableAtomic{

	
  private double PROCTIME = 10;
  private String myName = null;
  private int numberOfPorts;
  private String routingType;
  private int []portArray;
  private int length;
  private int MYNUMBER=0;
  
  protected double int_gen_time;
  int currentpacket_source = 0;
  int currentpacket_dest = 0;
  protected int count;
  protected rand r;
  packetEntity currentPacket,tobeQueuedPacket = null;
  protected DEVSQueue q;
  public WiredNode() {this(0,1,null,"hotpotato",1,1);}

public WiredNode(int nodenumber, int outputPorts,int []portArr, String routing_type, double proc_time, int len){
	
   super("WN"+nodenumber);
   myName = "WN"+nodenumber;
   MYNUMBER=nodenumber;
   numberOfPorts = outputPorts;
   routingType=routing_type;
   PROCTIME = proc_time;
   portArray=portArr;
   length=len;
   addInport("in");
   if(portArr == null){
	   for(int i=1;i<=outputPorts;i++)
	   	{
	   			addOutport("out"+i);
	   	}
   }
   else{
	   for(int i=0;i<outputPorts;i++)
	   	{
	   			addOutport("out"+portArr[i]);
	   	}
   }
   //addInport("selfPktIn");
}

public void initialize(){
	q = new DEVSQueue();
	passivate();
}


public void  deltext(double e,message x)
{
Continue(e);
	if (phaseIs("passive")){
	   for (int i=0; i< x.getLength();i++){
	     //if (messageOnPort(x, "selPktIn", i) || messageOnPort(x, "in", i)) { 
		   if (messageOnPort(x, "in", i)){ 
	       currentPacket = (packetEntity) x.getValOnPort("in", i);
	       currentpacket_source = currentPacket.getSrc();
	       currentpacket_dest = currentPacket.getDest();
	       holdIn("active", PROCTIME);
	     }
	   }
	}
	else if(phaseIs("active")){
	    for (int i=0; i< x.getLength();i++){
	      if (messageOnPort(x, "in", i)) {
	    	packetEntity tobeQueuedPacket = (packetEntity) x.getValOnPort("in", i);
	        q.add(tobeQueuedPacket);
	      }
	    }
	 }
}


public void  deltint( )
{
   if(phaseIs("active")){
	   if(!q.isEmpty()) {
	     currentPacket = (packetEntity)q.first();
	     currentpacket_source = currentPacket.getSrc();
	     currentpacket_dest = currentPacket.getDest();
	     holdIn("active", PROCTIME);    
	     q.remove();
	   }
   }
	   
	   
	   else
	          passivate();
	 
 

}

public message  out( )
{
	String destName = "WN"+currentpacket_dest;
   if(destName.equals(myName)){
	   //packet has reached destination!
	   //System.out.println("Recieved packet from : "+currentpacket_source+"\n");
   }
   else{
	   message  m = new message();
	   String gateOut = "out1";
	   if(routingType.equals("hotpotato")){
		   Random rand = new Random();
		   int randomNum = rand.nextInt(numberOfPorts) + 1;
		   if(portArray == null)
		   {
			   gateOut = "out"+randomNum;
		   }
		   else{
			   gateOut = "out"+portArray[randomNum-1];
		   }
		   
	   }
	   else if(routingType.equals("manhattan"))
	   {   //routing logic
		   
		   int direction=currentpacket_dest-MYNUMBER;
		   if( direction >= length){
			   //send down
			   gateOut = "out"+4;
		   }
		   else if((-direction) >= length){
			   //send up
			   gateOut = "out"+2;
		   }
		   else if(direction < length && direction >0){
			   //send right
			   gateOut = "out"+1;
		   }
		   else if((-direction) < length && (-direction) >0){
			   //send left
			   gateOut = "out"+3;
		   }
	   }
	   
	   content con = makeContent(gateOut, new packetEntity("packet:"+currentpacket_source+":"+currentpacket_dest, currentpacket_source,currentpacket_dest));
	   m.add(con);
	   return m;
   }
   return null;
}


}

