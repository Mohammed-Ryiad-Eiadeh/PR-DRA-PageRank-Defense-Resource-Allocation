# PR-DRA: PageRank-based Defense Resource Allocation Methods for Securing Interdependent Systems Modeled by Attack Graphs

# Paper Link: https://link.springer.com/article/10.1007/s10207-024-00964-3

# Abstract

Interdependent systems confront rapidly growing cybersecurity threats. This paper delves into the realm of security decision-making within these complex interdependent systems. We design a resource allocation framework to improve the security of interdependent systems managed by a single rational (logical) defender. Our framework models these systems and their potential attack vulnerabilities using the notion of cyber attack graphs. We propose three defense mechanisms, incorporating a popular network analysis algorithm called PageRank which is used to identify the importance of different critical assets in the system. These mechanisms stem from existing graph theories widely used in graphical models (including Adjacent Nodes, In-degree Nodes, and Markov Blanket). We adopt two versions of the PageRank algorithm to extract useful information about the attack graphs we use. Our approaches show low sensitivity to the number of concurrent attacks on interdependent systems. We evaluate our decision-making framework via ten attack graphs, which include multiple real-world interdependent systems. We quantify the level of security improvement under our defense methods compared to two well-known resource allocation algorithms and other proposed approaches. Our proposed framework leads to better resource allocations compared to these algorithms in most test cases. According to our results and the Wilcoxon and Friedman statistical tests, our approach’s outcomes are superior. Our framework enhances security decision-making under various circumstances. We release the full implementation of our framework for the research community.

# Framework

The figure depicts an attack graph consisting of entry nodes (used by attackers) and asset nodes (critical for defenders). Interconnections between these nodes represent security vulnerabilities. The Genetic Algorithm (GA) generates potential attack paths based on this graph. GA employs a path encoding criterion to prevent revisiting nodes and encodes sink nodes. A multi-objective fitness function evaluates feasible attack paths, considering asset loss. Defender allocate security budget across set of edges, guided by graph theory concepts (in-degree nodes, adjacent nodes, Markov Blanket, and min-cut edges). The PageRank algorithm informs asset importance, aiding proportional security investments. Finally, relative reduction in expected security cost is computed for performance analysis.

![Screenshot (1)](https://github.com/user-attachments/assets/0d39009b-1602-47fa-974a-9a8fa34b9f3a)

# Fitness Function

$F_2(P) = \max_{P \in P_m} \big(\exp\big(-\sum_{(v_i,v_j)\in P} {x_{i,j}}\big) + Wf\sum_{v_m\in P} L_m\big).$
   
   $P$ is the given attack path.

   $P_m$ is a set of attack paths.

   $v_i,v_j$ are the nodes in $P$.

   $L_m$ is the loss corresponding to node $v_m$

   $Wf$ is the weight factor lies in [0,1]
   
This function accounts for the total asset loss that the system will lose if the attack is occured successfully.

# Our Contribution

- We propose a *security resource allocation* method for securing interdependent systems, where assets are interconnected. We demonstrate how **\name** influences security decision-making and quantify the security improvement from our approach.

- We introduce a PageRank-based approach to enhance resource allocations. Our approach considers the importance (rank) of each critical asset in the interdependent system. This approach is applied within four graph-theoretic defense strategies: adjacent nodes, in-degree nodes, Markov blanket, and min-cut to address various attacks.

- We apply a Genetic Algorithm (GA) to identify the most probable attack paths from entry nodes to critical assets, ensuring manageable time complexity. The GA finds the top attack path for each entry-asset pair, simulates concurrent attack scenarios, and uses a multi-objective fitness function based on the estimated financial loss and the risk of each path.

- We evaluate our defense strategies using ten attack graphs, comparing the performance of PR-DRA with four baselines: defense in depth, behavioral defender, min-cut edges, and risk-based defense.

# Datasets We Used In Our Work

For our assessment, we used ten distinct attack graphs, each symbolizing a different interdependent system and network structure. We divided these datasets into three groups. The first group contains four attack graphs from real-world interconnected systems, namely DER.1, SCADA, E-commerce, and VOIP. Signifies an attack step, and we consider every edge to be directional. The second group consists of two graph typologies, referred to as HG1 and HG2, which were introduced in earlier studies. The third group includes four datasets from a renowned interactive scientific graph data repository, named aves-sparrow-social-2009 (ASC2009), aves-sparrowlyon-flock-season3 (ASFS3), aves-weaver-social-03 (AWS03), and aves-barn-swallow-non-physical (ABSNP). This repository is a network data collection produced by top-tier US niversities.

| System | # Nodes | # Edges | # Critical Assets | Graph Type |
| --- | --- | --- | --- | --- |
| SCADA [12] | 13 | 20 | 6 | Directed |
| DER.1 [13] | 22 | 32 | 6 | Directed |
| E-Commerce [14] | 20 | 32 | 4 | Directed |
| VOIP [14] | 22 | 35 | 4 | Directed |
| HG1 [15] | 7 | 10 | 2 | Directed |
| HG2 [15] | 15 | 22 | 5 | Directed |
| ABSNP [16] | 17 | 122 | 6 | Directed |
| ASFS3 [16] | 27 | 163 | 9 | Directed |
| ASS2009 [16] | 31 | 211 | 9 | Directed |
| AWS03 [16] | 42 | 152 | 15 | Directed |

Note: all of these datasets are stored in the project directory and is called dynamically so no need to set up their paths.

# Parameter Configuration of Our Experiments

We begin by detailing the primary hyperparameters utilized in various components of our framework. The parameters for the GA were selected as follows: maximum iterations ($M=1000$), population size which refers to a set of potential attack paths ($N=500$), mating probability ($m_p=0.2$), mutation rate ($m_r=0.2$), and weight factor ($Wf=0.001$). The defender's security security budgets is set at $S=5$ (unless otherwise stated for specific experiments), and the maximum iterations ($PR_{iter}$) for the PR algorithm is set at 100, with epsilon ($\epsilon$) set at 0.00001. We underscore that the benefits of our suggested defense (resource allocation) strategies are applicable for any given security budget. For the behavioral defender baseline~\cite{Abdallah2020}, we have set the behavioral level ($a$) at 0.5.

# Conclusion

This study introduces an effective defensive resource allocation strategy for understanding the impact of decision-making processes on the security of interdependent systems with a single rational defender. Initially, we model these interdependent systems and their potential attack vulnerabilities using cyber attack graphs. We propose three defense resource allocation mechanisms: Adjacent Nodes Defense, where the defender allocates resources on the edges connecting neighboring nodes to critical assets; In-Degree Defense, where resources are allocated on the edges connecting critical assets with their parents; and Markov Blanket Defense, where the defender distributes resources on the edges connecting each critical asset with its parents and children. Importantly, we employ the PageRank algorithm to rank each critical asset based on incoming and outgoing links and their weights. This ranking helps decide the proportion of each asset from the available budget. These approaches exhibit low sensitivity to the number of attacks and their entry nodes. Our strategies aid decision-makers in allocating their budget on attack paths to bolster interdependent systems’ security. We evaluate our different resource allocation strategies under varying parameters and concurrent attacks, validating them on real-world interdependent systems and attack graph datasets. Compared to existing methods, our strategies significantly enhance security.

# How To Run The Code (read carefully please)

1) Download intellIJ IDEA latest version
2) Dounload JDK 17 or higher
3) Set up the environment variable for the bin folder of the JDK 17+
4) Open the IDEA
5) Open the project
6) Make sure you are connected to the internet
7) Wait while the IDEA download all the libraries that are included as dependencies in the pom XML file
8) Go to the main file
9) Set up the desired hyperparameters (i.e., data path for your attack graph)
10) Run the file to see the results.

# References

[12] A. R. Hota, A. Clements, S. Sundaram, and S. Bagchi. 2016. Optimal and game-theoretic deployment of security investments in interdependent assets. In International Conference on Decision and Game Theory for Security. 101–113.

[13] S. Jauhar, B. Chen, W. G. Temple, X. Dong, Z. Kalbarczyk, W. H. Sanders, and D. M. Nicol. 2015. Model-based cybersecurity assessment with nescor smart grid failure scenarios. In Dependable Computing (PRDC), 2015 IEEE 21st Pacific Rim International Symposium on. IEEE, 319–324.

[14] G. Modelo-Howard, S. Bagchi, and G. Lebanon. 2008. Determining placement of intrusion detectors for a distributed application through bayesian network modeling. In International Workshop on Recent Advances in Intrusion Detection. Springer, 271–290.

[15] J. Zeng, S. Wu, Y. Chen, R. Zeng, and C. Wu, ‘‘Survey of Attack Graph Analysis Methods from the Perspective of Data and Knowledge Processing,’’ Security and Communication Networks, vol. 2019, 2019.

[16] R. A. Rossi and N. K. Ahmed, ‘‘The network data repository with interactive graph analytics and visualization,’’ in Proceedings of the Twenty-Ninth AAAI Conference on Artificial Intelligence, 2015. [Online]. Available: http://networkrepository.com.

# Contact With The Authors

Send email to the following Authors for any question about this work, and it is our pleasure to ansawer your question.

Mohammad Aleiadeh, maleiade@purdue.edu

Mustafa Abdallah, abdalla0@purdue.edu
