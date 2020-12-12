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
public class AlgoritmoAnchura extends SearchAlgorithm /*implements SearchProblem*/{
    public ArrayList<Action> resultado = new ArrayList<Action>();
    
    @Override
    public void searchPath() {
        boolean control = false;
        
        HashSet<State> cerrados = new HashSet<State>();
        Queue<Node> abiertos = new LinkedList<Node>();
        ArrayList<Node> sucesores = new ArrayList<Node>();

        
        System.out.println("Nodo inicial: "+problem.initialState()); 
        System.out.println("Nodo final: " + problem.goalState());

        abiertos.add(new Node(problem.initialState()));
           
        while(control == false){
         
        if(!abiertos.peek().getState().equals(problem.goalState())){
            System.out.println("abiertos: "+abiertos);
            if(cerrados.contains(abiertos.peek().getState())){
                abiertos.remove();
            }
            else{
                sucesores = getSuccessors(abiertos.peek());
                System.out.println("sucesores: "+sucesores);
                cerrados.add(abiertos.peek().getState());
                abiertos.remove();
                
                while(!sucesores.isEmpty()){
                    abiertos.add(sucesores.get(0));
                    sucesores.remove(0);
                }
            }
        }
        else{
           control = true; 
           
           System.out.println("ejecutando");
           Stack<Node> nodos = new Stack<Node>();
           nodos.add(abiertos.peek());
           
           while(nodos.peek().getState()!=problem.initialState()){
               //resultado.add(nodos.peek().getAction());
              
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

    
        return resultado;        
    }
}