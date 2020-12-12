import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yo-PC
 */
public class AlgoritmoPrimeroMejor extends SearchAlgorithm /*implements SearchProblem*/{
    public ArrayList<Action> resultado = new ArrayList<Action>();
    
    @Override
    public void searchPath() {
        boolean control = false;
        int i=0;
        HashSet<State> cerrados = new HashSet<State>();
        ArrayList<Node> abiertos = new ArrayList<Node>();
        ArrayList<Node> sucesores = new ArrayList<Node>();
        
        System.out.println("Nodo inicial: "+problem.initialState()); 
        System.out.println("Nodo final: " + problem.goalState());
        
        abiertos.add(new Node(problem.initialState()));
           
        while(control == false){
         
        if(!abiertos.get(0).getState().equals(problem.goalState())){
            
            if(cerrados.contains(abiertos.get(0).getState())){
                abiertos.remove(0);
            }
            else{
                sucesores = getSuccessors(abiertos.get(0));
                System.out.println("expandiendo : " + abiertos.get(0));
                cerrados.add(abiertos.get(0).getState());
                abiertos.remove(0);
                
                while(!sucesores.isEmpty()){
                    
                    if (abiertos.isEmpty()){
                        abiertos.add(sucesores.get(0));
                        sucesores.remove(0);
                    }
                    else if (sucesores.get(0).getHeuristic()>abiertos.get(abiertos.size()-1).getHeuristic()){
                        abiertos.add(abiertos.size(),sucesores.get(0));
                        sucesores.remove(0);
                    }
                    else if (sucesores.get(0).getHeuristic()<=abiertos.get(i).getHeuristic())
                    {
                        
                        abiertos.add(i,sucesores.get(0));
                        sucesores.remove(0);
                    }
                    
                    else{
                        i++;
                    }
                }
            }
        }
        else{
           control = true; 
           
           
           Stack<Node> nodos = new Stack<Node>();
           nodos.add(abiertos.get(0));
           
           while(nodos.peek().getState()!=problem.initialState()){
               
              
               nodos.add(nodos.peek().getParent());
               
               System.out.println("nodos: "+ nodos);
           }
           
           while(!nodos.isEmpty()){
               if(!nodos.peek().getState().equals(problem.initialState())){
                   System.out.println("coste: " + nodos.peek().getCost());
           resultado.add(nodos.pop().getAction());
               }
               else
                   nodos.pop();
               
           }
           System.out.println("acciones: "+ resultado);
        result();
        }     
        }
    }

    @Override
    public ArrayList<Action> result() {
        
        System.out.println("resultado en result: "+resultado);
        //Collections.reverse(resultado);
        
//        resultado.add(Action.DOWN);
//        resultado.add(Action.DOWN);
//        resultado.add(Action.DOWN);
    
        return resultado;        
    }
}