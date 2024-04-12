import java.util.*;

public class ElectionSystem {

    private static final String[] ALL_CANDIDATES = {
            "Marcus Fenix", "Dominic Santiago", "Bird Watcher", "Cole Miner", "Anya Stroud",
            "Saidmakhmud Makhkamjonov", "Mjolnier Handler", "Jake Sully", "Jack Frost", "Jay Cole"
    };

    public static void main(String[] args) {
        Random random = new Random();

        int numCandidates = random.nextInt(ALL_CANDIDATES.length - 3) + 3;

        LinkedList<String> candidates = new LinkedList<>();
        List<String> allCandidatesList = Arrays.asList(ALL_CANDIDATES);
        Collections.shuffle(allCandidatesList);
        for (int i = 0; i < numCandidates; i++) {
            candidates.add(allCandidatesList.get(i));
        }

        int numberOfVotes = random.nextInt(401) + 100;

        Election election = new Election();
        election.initializeCandidates(candidates);
        System.out.println("Election Candidates: " + candidates);
        System.out.println("Total Electorate Votes: " + numberOfVotes);

        for (int i = 0; i < numberOfVotes; i++) {
            election.castRandomVote();
        }

        List<String> topCandidates = election.getTopKCandidates(3);
        System.out.println("Top 3 candidates after " + numberOfVotes + " votes: " + topCandidates);

        election.auditElection();
    }
}