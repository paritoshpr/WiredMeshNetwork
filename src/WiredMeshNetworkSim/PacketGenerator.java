package WiredMeshNetworkSim;






import simView.*;

import java.lang.*;
import java.util.Random;

import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import util.*;
import statistics.*;

public class PacketGenerator extends ViewableAtomic{

  private int MYNUMBER;
  private int numberOfNodes = 0;
  protected double int_gen_time;
  protected int count;
  protected rand r;

  public PacketGenerator() {this(1,7,1);}

public PacketGenerator(int mynumber, double period, int numnodes){
   super("PG"+mynumber);
   addInport("in");
   addOutport("out");
   
   
   MYNUMBER = mynumber;
   numberOfNodes = numnodes;
   int_gen_time = period ;
}

public void initialize(){
   holdIn("active", int_gen_time);
   r = new rand(123987979);
   count = 0;
}


public void  deltext(double e,message x)
{
Continue(e);
   for (int i=0; i< x.getLength();i++){
     if (messageOnPort(x, "in", i)) { //the stop message from tranducer
       passivate();
     }
   }
}


public void  deltint( )
{

if(phaseIs("active")){
   count = count +1;
//   holdIn("active",int_gen_time);
   holdIn("active",6+r.uniform(int_gen_time));
}
else passivate();
}

public message  out( )
{
//System.out.println(name+" out count "+count);
   message  m = new message();
//   content con = makeContent("out", new entity("car" + count));
  int currentpacket_source=MYNUMBER;
  int randomNum = 0;
  //generate random destination
  do{
	  Random rand = new Random();
	  randomNum = rand.nextInt(numberOfNodes) + 1;
  }while(randomNum==MYNUMBER);
  
  int currentpacket_dest = randomNum; //random destination
   content con = makeContent("out", new packetEntity("packet:"+currentpacket_source+":"+currentpacket_dest, currentpacket_source,currentpacket_dest));
   m.add(con);

  return m;
}


}

