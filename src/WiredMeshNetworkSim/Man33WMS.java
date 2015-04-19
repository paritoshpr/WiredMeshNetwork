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

import java.awt.*;
import java.io.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;


public class Man33WMS extends ViewableDigraph{

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
	private int LENGTH =3;
	private int BREADTH = 3;
	private int NUMNODES = LENGTH*BREADTH; 
	public Man33WMS(){
    this("WiredMeshNetwork");
}

public Man33WMS(String nm){
    super(nm);
    WiredMeshSysConstruct();
}

public void WiredMeshSysConstruct(){

    this.addOutport("out");
    
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
				int nodeNum=(i-1)*LENGTH+(j);
				int nodeIdx = nodeNum-1;
				
				if(i==1){
					if(j==1){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{1,4},routingtype,2,LENGTH);
					}
					else if(j==LENGTH){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{3,4},routingtype,2,LENGTH);
					}
					else{
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{1,3,4},routingtype,2,LENGTH);
					}
				}
				else if(i== BREADTH){
					if(j==1){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{1,2},routingtype,2,LENGTH);
					}
					else if(j==LENGTH){
						Node[nodeIdx]=new WiredNode(nodeNum,2,new int[]{2,3},routingtype,2,LENGTH);
					}
					else{
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{1,2,3},routingtype,2,LENGTH);
					}
				}
				else{
					if(j==1){
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{1,2,4},routingtype,2,LENGTH);
					}
					else if(j==LENGTH){
						Node[nodeIdx]=new WiredNode(nodeNum,3,new int[]{2,3,4},routingtype,2,LENGTH);
					}
					else{
						Node[nodeIdx]=new WiredNode(nodeNum,4,new int[]{1,2,3,4},routingtype,2,LENGTH);
					}
				}
		    	
		    	
		    	PG[nodeIdx]=new PacketGenerator(nodeNum,1,NUMNODES);//nodeId,period,NUMNODES
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
        preferredSize = new Dimension(934, 453);
        if((ViewableComponent)withName("WN2")!=null)
             ((ViewableComponent)withName("WN2")).setPreferredLocation(new Point(172, 20));
        if((ViewableComponent)withName("WN3")!=null)
             ((ViewableComponent)withName("WN3")).setPreferredLocation(new Point(355, 23));
        if((ViewableComponent)withName("PG8")!=null)
             ((ViewableComponent)withName("PG8")).setPreferredLocation(new Point(173, 314));
        if((ViewableComponent)withName("WN5")!=null)
             ((ViewableComponent)withName("WN5")).setPreferredLocation(new Point(172, 139));
        if((ViewableComponent)withName("WN6")!=null)
             ((ViewableComponent)withName("WN6")).setPreferredLocation(new Point(355, 143));
        if((ViewableComponent)withName("PG6")!=null)
             ((ViewableComponent)withName("PG6")).setPreferredLocation(new Point(355, 187));
        if((ViewableComponent)withName("PG5")!=null)
             ((ViewableComponent)withName("PG5")).setPreferredLocation(new Point(172, 193));
        if((ViewableComponent)withName("PG1")!=null)
             ((ViewableComponent)withName("PG1")).setPreferredLocation(new Point(1, 62));
        if((ViewableComponent)withName("WN4")!=null)
             ((ViewableComponent)withName("WN4")).setPreferredLocation(new Point(-1, 139));
        if((ViewableComponent)withName("PG7")!=null)
             ((ViewableComponent)withName("PG7")).setPreferredLocation(new Point(-2, 314));
        if((ViewableComponent)withName("WN9")!=null)
             ((ViewableComponent)withName("WN9")).setPreferredLocation(new Point(353, 264));
        if((ViewableComponent)withName("WN1")!=null)
             ((ViewableComponent)withName("WN1")).setPreferredLocation(new Point(1, 17));
        if((ViewableComponent)withName("PG3")!=null)
             ((ViewableComponent)withName("PG3")).setPreferredLocation(new Point(355, 69));
        if((ViewableComponent)withName("PG2")!=null)
             ((ViewableComponent)withName("PG2")).setPreferredLocation(new Point(172, 66));
        if((ViewableComponent)withName("WN8")!=null)
             ((ViewableComponent)withName("WN8")).setPreferredLocation(new Point(173, 266));
        if((ViewableComponent)withName("PG9")!=null)
             ((ViewableComponent)withName("PG9")).setPreferredLocation(new Point(353, 309));
        if((ViewableComponent)withName("PG4")!=null)
             ((ViewableComponent)withName("PG4")).setPreferredLocation(new Point(-1, 186));
        if((ViewableComponent)withName("WN7")!=null)
             ((ViewableComponent)withName("WN7")).setPreferredLocation(new Point(-2, 266));
    }
}
