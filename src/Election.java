import java.util.*;

public class Election {
    private final Map<String, Integer> voteCounts = new HashMap<>();
    private final List<String> candidates = new ArrayList<>();
    private final int electorateSize;
    private int totalVotes = 0;
    private final Random random = new Random();

    /**
     * @param electorateSize the total number of votes that will be cast before rigging
     */
    public Election(int electorateSize) {
        this.electorateSize = electorateSize;
    }

  // Initialize with the given list of candidates (all start at 0 votes). */
    public void initializeCandidates(LinkedList<String> initialCandidates) {
        candidates.clear();
        candidates.addAll(initialCandidates);
        voteCounts.clear();
        for (String c : initialCandidates) {
            voteCounts.put(c, 0);
        }
        totalVotes = 0;
    }

   // Cast one vote for the named candidate. */
    public void castVote(String candidate) {
        if (!voteCounts.containsKey(candidate)) {
            throw new IllegalArgumentException("Unknown candidate: " + candidate);
        }
        voteCounts.put(candidate, voteCounts.get(candidate) + 1);
        totalVotes++;
    }

    /** Cast one vote for a random candidate. */
    public void castRandomVote() {
        String pick = candidates.get(random.nextInt(candidates.size()));
        castVote(pick);
    }


    public void rigElection(String candidate) {
        if (totalVotes != electorateSize) {
            throw new IllegalStateException(
                    "Must cast all " + electorateSize + " votes before rigging; have cast " + totalVotes);
        }
        if (!voteCounts.containsKey(candidate)) {
            throw new IllegalArgumentException("Unknown candidate: " + candidate);
        }

        // Keep taking one vote from the current highest opponent and give it to `candidate`
        while (true) {
            int targetVotes = voteCounts.get(candidate);

            // find the highest‐voted other candidate
            String topOther = null;
            int topOtherVotes = -1;
            for (Map.Entry<String, Integer> e : voteCounts.entrySet()) {
                if (e.getKey().equals(candidate)) continue;
                if (e.getValue() > topOtherVotes) {
                    topOtherVotes = e.getValue();
                    topOther = e.getKey();
                }
            }

            // if candidate already beats everyone, we’re done
            if (targetVotes > topOtherVotes) {
                break;
            }

            // move one vote
            voteCounts.put(topOther, topOtherVotes - 1);
            voteCounts.put(candidate, targetVotes + 1);
        }
    }

    /**
     * @return the top k candidates (by vote count), in descending order of votes
     */
    public List<String> getTopKCandidates(int k) {
        // max‐heap by vote count
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue())
        );
        pq.addAll(voteCounts.entrySet());

        List<String> topK = new ArrayList<>();
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            topK.add(pq.poll().getKey());
        }
        return topK;
    }

    /** Print all candidates from most votes to fewest, with their vote counts. */
    public void auditElection() {
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue())
        );
        pq.addAll(voteCounts.entrySet());

        while (!pq.isEmpty()) {
            Map.Entry<String, Integer> e = pq.poll();
            System.out.println(e.getKey() + " - " + e.getValue());
        }
    }
}
