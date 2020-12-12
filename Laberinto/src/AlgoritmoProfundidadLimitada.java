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
public class AlgoritmoProfundidadLimitada extends SearchAlgorithm /*implements SearchProblem*/{
    static int limit = 15;
    
    public ArrayList<Action> resultado = new ArrayList<Action>();
    
    @Override
    public void searchPath() {
        int exp=0;
        //System.out.println(a);
        boolean control = false;
        
        HashSet<State> cerrados = new HashSet<State>();
        Stack<Node> abiertos = new Stack<Node>();
        ArrayList<Node> sucesores = new ArrayList<Node>();
        
        
        System.out.println("Nodo inicial: "+problem.initialState()); 
        System.out.println("Nodo final: " + problem.goalState());
        
        abiertos.add(new Node(problem.initialState()));
           
        while(control == false){
        if (abiertos.isEmpty()){
            System.out.println("No se ha encontrado camino, intente con un limite mayor");
            return;
        }
        if(!abiertos.peek().getState().equals(problem.goalState())){
            
            if(cerrados.contains(abiertos.peek().getState())){
                abiertos.pop();
            }
            else{
                sucesores = getSuccessors(abiertos.peek());
                cerrados.add(abiertos.peek().getState());
                System.out.println("abiertos: "+ abiertos);
                abiertos.pop();
                
                while(!sucesores.isEmpty() && sucesores.get(0).getDepth()<=limit){
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
        //Collections.reverse(resultado);
        
//        resultado.add(Action.DOWN);
//        resultado.add(Action.DOWN);
//        resultado.add(Action.DOWN);
    
        return resultado;        
    }
}