package Main;

import Attack_Defence_Graph.org.GraphData;
import ConcurrentAttacks.org.ConcurrentAttack;
import ResourceAllocationsCR.org.AllocationApproaches;
import StoringResults.org.StoreDataAsTable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResourceAllocationMain implements StoreDataAsTable {
    public static void main(String[] args) {
        // Select all test cases or the graphs as once.
        var pathURL = "DataFilePath";
        List<String> listOfAttackGraphs = new ArrayList<>();
        Path folder = Path.of(pathURL);
        if (Files.exists(folder) && Files.isDirectory(folder)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folder, "*.txt")) {
                for (Path path : directoryStream) {
                    listOfAttackGraphs.add(path.getFileName().toString());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Define a string array to store the name of the allocation approach as the header of the performance matrix
        var headers = new String[14];

        // Define a hashmap that maps each graph with the allocation approaches sorted for it
        var mapGraphsToAllocationSortedMethods = new HashMap<String, HashMap<String, Double>>();

        // Iterate over all graph cases we have
        for (String graphCase : listOfAttackGraphs) {
            graphCase = graphCase.replace(".txt", "");
            var task = new GraphData(Path.of(pathURL + "\\" + graphCase + ".txt"));
            var attackDefenceGraph = task.getAttackDefenceGraph();
            var AdjMat = task.getAdjacencyMatrix(attackDefenceGraph);
            var assetLossVec = task.getNodeAssetsLossValues();

            // Generate paths by genetic algorithm from each entry node to each asset
            var concurrentAttackers = new ConcurrentAttack(AdjMat,
                assetLossVec,
                500,
                0.2,
                0.4,
                0.6,
                1000);
            var concurrentAttacks = concurrentAttackers.getTop_1_Paths();

            // Define the security Budget
            int resources = 5;

            // Define a hashmap that maps the allocation to their cost relative reduction
            var mapAllocationMethodToRelativeCostReduction = new HashMap<String, Double>();

            // Define and initialize the array to hold the relative cost reduction from each allocation approach
            double[] scoresRow = new double[14];

            // Calculate the expected relative reduction in the cost after allocating resources using defense in depth
            var defenceInDepth = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            defenceInDepth.callDefenseInDepth();
            headers[0] = "defenceInDepth";
            scoresRow[0] = defenceInDepth.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[0], scoresRow[0]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using behavioral defender
            var behavioralDefender = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            behavioralDefender.callBehavioralDefender();
            headers[1] = "behavioralDefender";
            scoresRow[1] = behavioralDefender.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[1], scoresRow[1]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using behavioral defender
            var minCut = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            minCut.callMinCut();
            headers[2] = "minCut";
            scoresRow[2] = minCut.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[2], scoresRow[2]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using risk based defense
            var riskBasedDefence = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            riskBasedDefence.callRiskBasedDefense();
            headers[3] = "riskBasedDefence";
            scoresRow[3] = riskBasedDefence.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[3], scoresRow[3]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using risk based defense
            var AdjacentNodesDefence = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            AdjacentNodesDefence.callAdjacentNodes();
            headers[4] = "adjacentNodesDefence";
            scoresRow[4] = AdjacentNodesDefence.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[4], scoresRow[4]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using risk based defense
            var InDegreeNodesDefence = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            InDegreeNodesDefence.callInDegreeNodes();
            headers[5] = "inDegreeNodesDefence";
            scoresRow[5] = InDegreeNodesDefence.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[5], scoresRow[5]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using risk based defense
            var MarkovBlanketDefence = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            MarkovBlanketDefence.callMarkovBlanket();
            headers[6] = "MarkovBlanketDefence";
            scoresRow[6] = MarkovBlanketDefence.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[6], scoresRow[6]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + Markov blanket
            var PRJGraphTCentralityWithMarkovBlanket = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRJGraphTCentralityWithMarkovBlanket.callCentrality(AllocationApproaches.LinkageType.Markov_Blanket, AllocationApproaches.PR_Version.PR_JGraphT);
            headers[7] = "PRJGraphTCentralityWithMarkovBlanket";
            scoresRow[7] = PRJGraphTCentralityWithMarkovBlanket.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[7], scoresRow[7]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + Adjacent nodes
            var PRJGraphTCentralityWithAdjacentNodes = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRJGraphTCentralityWithAdjacentNodes.callCentrality(AllocationApproaches.LinkageType.Adjacent_Nodes, AllocationApproaches.PR_Version.PR_JGraphT);
            headers[8] = "PRJGraphTCentralityWithAdjacentNodes";
            scoresRow[8] = PRJGraphTCentralityWithAdjacentNodes.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[8], scoresRow[8]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + In-Degree nodes
            var PRJGraphTCentralityWithInDegreeNodes = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRJGraphTCentralityWithInDegreeNodes.callCentrality(AllocationApproaches.LinkageType.In_Degree_Nodes, AllocationApproaches.PR_Version.PR_JGraphT);
            headers[9] = "PRJGraphTCentralityWithInDegreeNodes";
            scoresRow[9] = PRJGraphTCentralityWithInDegreeNodes.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[9], scoresRow[9]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            /*// Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + Markov blanket
            var PRJGraphTCentralityWithMinCutEdges = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRJGraphTCentralityWithMinCutEdges.callCentrality(AllocationApproaches.LinkageType.Min_Cut_Edges, AllocationApproaches.PR_Version.PR_JGraphT);
            // System.out.println("Relative cost reduction after the PR centrality + Markov Blanket allocation is: " + PRCentralityWithMarkovBlanket.getExpectedCostReduction());
            headers[14] = "PRJGraphTCentralityWithMinCutEdges";
            scoresRow[14] = PRJGraphTCentralityWithMinCutEdges.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[14], scoresRow[14]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);*/

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + Markov blanket
            var PRCentralityWithMarkovBlanket = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRCentralityWithMarkovBlanket.callCentrality(AllocationApproaches.LinkageType.Markov_Blanket, AllocationApproaches.PR_Version.PR_Ours);
            headers[10] = "PRCentralityWithMarkovBlanket";
            scoresRow[10] = PRCentralityWithMarkovBlanket.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[10], scoresRow[10]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + Adjacent nodes
            var PRCentralityWithAdjacentNodes = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRCentralityWithAdjacentNodes.callCentrality(AllocationApproaches.LinkageType.Adjacent_Nodes, AllocationApproaches.PR_Version.PR_Ours);
            headers[11] = "PRCentralityWithAdjacentNodes";
            scoresRow[11] = PRCentralityWithAdjacentNodes.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[11], scoresRow[11]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + In-Degree nodes
            var PRCentralityWithInDegreeNodes = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRCentralityWithInDegreeNodes.callCentrality(AllocationApproaches.LinkageType.In_Degree_Nodes, AllocationApproaches.PR_Version.PR_Ours);
            headers[12] = "PRCentralityWithInDegreeNodes";
            scoresRow[12] = PRCentralityWithInDegreeNodes.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[12], scoresRow[12]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);

            // Calculate the expected relative reduction in the cost after allocating resources using PageRank_PR_DRA centrality + Markov blanket
            var PRCentralityWithMinCutEdges = new AllocationApproaches(task, attackDefenceGraph, AdjMat, concurrentAttacks, assetLossVec, resources);
            PRCentralityWithMinCutEdges.callCentrality(AllocationApproaches.LinkageType.Min_Cut_Edges, AllocationApproaches.PR_Version.PR_Ours);
            headers[13] = "PRCentralityWithMinCutEdges";
            scoresRow[13] = PRCentralityWithMinCutEdges.getExpectedCostReduction();
            mapAllocationMethodToRelativeCostReduction.put(headers[13], scoresRow[13]);
            mapGraphsToAllocationSortedMethods.put(graphCase, mapAllocationMethodToRelativeCostReduction);
        }
        System.out.println("The allocation process has been completed");
        StoreDataAsTable.storeDataFromHashMap("Results", headers, mapGraphsToAllocationSortedMethods);
    }
}
