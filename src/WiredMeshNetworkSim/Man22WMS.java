package WiredMeshNetworkSim;





import simView.*;
import statistics.rand;

import java.awt.*;
import java.io.*;

import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;


public class Man22WMS extends ViewableDigraph{

	/*	
	 * EXAMPLE OF TOPOLOGY
	 * 	
	 * 	LENGTH=5,BREADTH=3,NUMNODES=15
	 *		1----2----3----4----5
	 * 		|	 |	  |    |    |
	 * 		6----7----8----9----10
	 * 		|    |    |    |    |
	 * 		11---12---13---14---15
	 * 
	 */
	
	
	//private String routingtype = "hotpotato";
	private String routingtype = "manhattan";
	private int LENGTH =2;
	private int BREADTH = 2;
	private int NUMNODES = LENGTH*BREADTH; 
	protected rand r;
	private double pg_time_base=4;
	public Man22WMS(){
    this("WiredMeshNetwork");
}

public Man22WMS(String nm){
    super(nm);
    WiredMeshSysConstruct();
}

public void WiredMeshSysConstruct(){

    this.addOutport("out");
    r = new rand(123987979);
    ViewableAtomic [] Node = new ViewableAtomic[NUMNODES];
    ViewableAtomic [] PG = new ViewableAtomic[NUMNODES];
    
    
    //gate creation logic
    /*			
     * 			2
     * 			|
     * 		3---N---1
     *			| 
     * 			4
     */
    
    
    for(int i=1;i<=BREADTH;i++)
	{
		for(int j = 1;j<= LENGTH; j++)
			{
				int nodeNum=(i-1)*LENGTH+(j-1);
				int nodeIdx = nodeNum;
				int gen_time= 2;
				//double proc_time=1+r.uniform(gen_time);
				double proc_time = 2;
				pg_time_base= 1+r.uniform(gen_time);
				if(i==1){
					if(j==1){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{1,4},routingtype,proc_time,LENGTH);
					}
					else if(j==LENGTH){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{3,4},routingtype,proc_time,LENGTH);
					}
					else{
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{1,3,4},routingtype,proc_time,LENGTH);
					}
				}
				else if(i== BREADTH){
					if(j==1){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{1,2},routingtype,proc_time,LENGTH);
					}
					else if(j==LENGTH){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{2,3},routingtype,proc_time,LENGTH);
					}
					else{
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{1,2,3},routingtype,proc_time,LENGTH);
					}
				}
				else{
					if(j==1){
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{1,2,4},routingtype,proc_time,LENGTH);
					}
					else if(j==LENGTH){
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{2,3,4},routingtype,proc_time,LENGTH);
					}
					else{
						Node[nodeIdx]=new WiredNode(nodeNum,4,new int[]{1,2,3,4},routingtype,proc_time,LENGTH);
					}
				}
		    	
		    	
		    	PG[nodeIdx]=new PacketGenerator(nodeNum,pg_time_base,NUMNODES);//nodeId,period,NUMNODES
		    	add(Node[nodeIdx]);
		    	add(PG[nodeIdx]);
		    	addCoupling(PG[nodeIdx],"out",Node[nodeIdx],"in");
		    	
			}
	}
    PG[0].setPreferredLocation(new Point(100, 100));
    //coupling logic
    for(int i=1;i<=BREADTH;i++)
		{
			for(int j = 1;j<= LENGTH; j++)
				{
					int nodeIdx=(i-1)*LENGTH+(j-1);
					if(i==1)
					{	addCoupling(Node[nodeIdx],"out"+4,Node[nodeIdx+LENGTH],"in");
						if(j==1){
							addCoupling(Node[nodeIdx],"out"+1,Node[nodeIdx+1],"in");
						}
						else if(j==LENGTH){		
							addCoupling(Node[nodeIdx],"out"+3,Node[nodeIdx-1],"in");
						}
						else{
							addCoupling(Node[nodeIdx],"out"+1,Node[nodeIdx+1],"in");
							addCoupling(Node[nodeIdx],"out"+3,Node[nodeIdx-1],"in");
						}
							
					}
					else if(i==BREADTH){
						
						addCoupling(Node[nodeIdx],"out"+2,Node[nodeIdx-LENGTH],"in");
						if(j==1){
							addCoupling(Node[nodeIdx],"out"+1,Node[nodeIdx+1],"in");
						}
						else if(j==LENGTH){		
							addCoupling(Node[nodeIdx],"out"+3,Node[nodeIdx-1],"in");
						}
						else{
							addCoupling(Node[nodeIdx],"out"+1,Node[nodeIdx+1],"in");
							addCoupling(Node[nodeIdx],"out"+3,Node[nodeIdx-1],"in");
						}
					}
					else{
						
						addCoupling(Node[nodeIdx],"out"+2,Node[nodeIdx-LENGTH],"in");
						addCoupling(Node[nodeIdx],"out"+4,Node[nodeIdx+LENGTH],"in");
						if(j==1){
							addCoupling(Node[nodeIdx],"out"+1,Node[nodeIdx+1],"in");
						}
						else if(j==LENGTH){		
							addCoupling(Node[nodeIdx],"out"+3,Node[nodeIdx-1],"in");
						}
						else{
							addCoupling(Node[nodeIdx],"out"+1,Node[nodeIdx+1],"in");
							addCoupling(Node[nodeIdx],"out"+3,Node[nodeIdx-1],"in");
						}
						
						
					}
						
				}
		}
    
    
   
}


    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1267, 582);
        if((ViewableComponent)withName("PG1")!=null)
             ((ViewableComponent)withName("PG1")).setPreferredLocation(new Point(173, 61));
        if((ViewableComponent)withName("WN2")!=null)
             ((ViewableComponent)withName("WN2")).setPreferredLocation(new Point(4, 133));
        if((ViewableComponent)withName("WN1")!=null)
             ((ViewableComponent)withName("WN1")).setPreferredLocation(new Point(171, 14));
        if((ViewableComponent)withName("WN3")!=null)
             ((ViewableComponent)withName("WN3")).setPreferredLocation(new Point(174, 133));
        if((ViewableComponent)withName("PG2")!=null)
             ((ViewableComponent)withName("PG2")).setPreferredLocation(new Point(4, 179));
        if((ViewableComponent)withName("WN0")!=null)
             ((ViewableComponent)withName("WN0")).setPreferredLocation(new Point(5, 17));
        if((ViewableComponent)withName("PG0")!=null)
             ((ViewableComponent)withName("PG0")).setPreferredLocation(new Point(4, 64));
        if((ViewableComponent)withName("PG3")!=null)
             ((ViewableComponent)withName("PG3")).setPreferredLocation(new Point(175, 181));
    }
}
