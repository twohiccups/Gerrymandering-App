package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import algorithm.Metric;
import algorithm.ObjectiveFunction;
import algorithm.RegionGrowing;
import algorithm.SimulatedAnnealing;
import com.google.gson.Gson;
import dataTypes.StateName;
import java.awt.Toolkit;
import manager.StateManager;
import mapObjects.State;
import seeding.IncumbentSeedStrategy;
import seeding.RandomSeedStrategy;
import seeding.SeedStrategy;

public class MapServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("enter this dumb servlets");
        System.out.println("enter this dumb servlets");
        System.out.println("araygo");
        Toolkit.getDefaultToolkit().beep();
        
        ObjectiveFunction objectiveFunction = new ObjectiveFunction(retrieveMetrics(request));
        SeedStrategy seedStrategy = new RandomSeedStrategy(); 
        StateName stateName = StateName.OREGON;
        State s = StateManager.getState(stateName.OREGON);
        
        String numString = (String)request.getAttribute("numDistricts"); 
        System.out.println(numString);
        int numDistricts =  ((numString == null) ||  (numString.isEmpty())) ? 5 : Integer.valueOf(numString);
        s.setNumDistricts(numDistricts);
        System.out.println(s.unassigned.getPrecincts().size());
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        int a = 10;
        response.getWriter().write((new Gson()).toJson(a));
//        response.
        
 

        Toolkit.getDefaultToolkit().beep();

        
        RegionGrowing regionGrowing = new RegionGrowing(
                s,
                objectiveFunction,
                seedStrategy
        );


        // now all servlets have access to the algorithms
        
        request.getSession().setAttribute("algorithm", regionGrowing);
        regionGrowing.run();
        Toolkit.getDefaultToolkit().beep();
        System.out.println("cum plete");
        
        
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(
                s,
                objectiveFunction
        );
        request.getSession().setAttribute("algorithm", simulatedAnnealing);
        simulatedAnnealing.run();

        
        
        
        
        
        
       
      
        
  
        
    }
    
    private Map<Metric, Double> retrieveMetrics(HttpServletRequest request) {
        Map<Metric, Double> metrics = new HashMap<Metric, Double>();
        metrics.put(Metric.COMPACTNESS, Double.valueOf(request.getParameter("compactness")));
        metrics.put(Metric.EFFICIENCYGAP, Double.valueOf(request.getParameter("polsby")));
        metrics.put(Metric.POLTSBY_POPPER, Double.valueOf(request.getParameter("polsby"))); 
        metrics.put(Metric.SCHWARTZBERG, Double.valueOf(request.getParameter("schwartzberg")));
        metrics.put(Metric.REOCK, Double.valueOf(request.getParameter("reock")));
        metrics.put(Metric.POPOULATIONEQUALITY, Double.valueOf(request.getParameter("populationEquality")));
        
        metrics.forEach((a, b) -> System.out.println(b)); 
        
//            var state = document.getElementById("state").value;
//        var numDistricts = document.getElementById("numDistrict").value;
//        var electionYear = document.getElementById("year").value;
//        var seedStrategy = document.getElementById("seedStrategy").value;



//        metrics.put(Metric.COMPACTNESS, );
//        metrics.put(Metric.POLTSBY_POPPER, 0.8);
//        metrics.put(Metric.SCHWARTZBERG, 0.0);
//        metrics.put(Metric.REOCK, 0.0);
//        metrics.put(Metric.PARTISANFAIRNESS, 0.0);
//        metrics.put(Metric.POPOULATIONEQUALITY, 0.0);
//        metrics.put(Metric.CONSISTENCY, 0.0);
//        metrics.put(Metric.GERRYMANDERING, 0.0);
//        metrics.put(Metric.ALIGNMENT, 0.1);
//        metrics.put(Metric.EFFICIENCYGAP, 0.1);
        return metrics;
    }
    
    private SeedStrategy retrieveSeedingStrategy(HttpServletRequest request) {
        SeedStrategy seedStrategy = null;
        if (request.getParameter("seedStrategy").equalsIgnoreCase("random")) {
            seedStrategy = new RandomSeedStrategy();
        } else if (request.getParameter("seedStrategy").equalsIgnoreCase("incumbent")) {
            seedStrategy = new IncumbentSeedStrategy();
        }
        return seedStrategy;
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
}
