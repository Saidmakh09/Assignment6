import java.util.*;

public class Election {
    private PriorityQueue<Candidate> maxHeap;
    private Map<String, Candidate> candidateMap;
    private int totalVotes;

    public Election() {
        this.maxHeap = new PriorityQueue<>((a, b) -> b.votes - a.votes);
        this.candidateMap = new HashMap<>();
    }

    public void initializeCandidates(LinkedList<String> candidates) {
        for (String candidateName : candidates) {
            Candidate candidate = new Candidate(candidateName, 0);
            candidateMap.put(candidateName, candidate);
            maxHeap.add(candidate);
        }
    }

    public void castVote(String candidate) {
        if (candidateMap.containsKey(candidate)) {
            Candidate current = candidateMap.get(candidate);
            maxHeap.remove(current);
            current.votes++;
            maxHeap.add(current);
            totalVotes++; // Update total votes whenever a vote is cast
        }
    }

    public void castRandomVote() {
        List<String> keys = new ArrayList<>(candidateMap.keySet());
        String randomKey = keys.get(new Random().nextInt(keys.size()));
        castVote(randomKey);
    }

    public void rigElection(String candidate) {
        if (!candidateMap.containsKey(candidate)) return;

        int maxVotes = maxHeap.peek().votes;
        Candidate current = candidateMap.get(candidate);
        int votesNeeded = maxVotes - current.votes + 1;

        for (int i = 0; i < votesNeeded; i++) {
            castVote(candidate);
        }
    }

    public List<String> getTopKCandidates(int k) {
        List<String> topK = new ArrayList<>();
        List<Candidate> temp = new ArrayList<>();

        while (k-- > 0 && !maxHeap.isEmpty()) {
            Candidate top = maxHeap.poll();
            topK.add(top.name);
            temp.add(top);
        }

        temp.forEach(maxHeap::add);
        return topK;
    }

    public void auditElection() {
        PriorityQueue<Candidate> temp = new PriorityQueue<>((a, b) -> b.votes - a.votes);
        temp.addAll(maxHeap);

        while (!temp.isEmpty()) {
            Candidate candidate = temp.poll();
            System.out.println(candidate.name + " - " + candidate.votes);
        }
    }

    private static class Candidate {
        String name;
        int votes;

        public Candidate(String name, int votes) {
            this.name = name;
            this.votes = votes;
        }
    }
}
