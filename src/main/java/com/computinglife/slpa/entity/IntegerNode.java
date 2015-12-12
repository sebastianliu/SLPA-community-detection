package com.computinglife.slpa.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.computinglife.slpa.util.RandomNumGenerator;

public class IntegerNode {
	int id;
	int community;
	HashMap<Integer, Integer> communityDistribution;

	public IntegerNode(int id, int community) {
		this.id = id;
		this.community = community;
		communityDistribution = new HashMap<Integer, Integer>();
	}

	public void updateCommunityDistribution(int votedCommunity, int voteIncrement) {

		if (communityDistribution.containsKey(votedCommunity))
			voteIncrement += communityDistribution.get(votedCommunity);

		communityDistribution.put(votedCommunity, voteIncrement);

	}

	// Vote for a community randomly based on the distribution of communities at
	// this node
	public int speakerVote() {

		// Run through each element in the map to create a cumulative
		// distribution
		Set<Integer> communityIds = communityDistribution.keySet();
		ArrayList<Integer> communities = new ArrayList<Integer>();
		ArrayList<Integer> cumulativeCounts = new ArrayList<Integer>();

		int sum = -1;
		for (Integer comm : communityIds) {
			sum += communityDistribution.get(comm);
			communities.add(comm);
			cumulativeCounts.add(sum);
		}

		// Generate a random integer in the range [0,sum)
		int rand = RandomNumGenerator.getRandomInt(sum + 1);

		// Find the index of first value greater than rand in cumulativeCounts
		int i = 0;
		for (i = 0; i < cumulativeCounts.size(); i++)
			if (cumulativeCounts.get(i) >= rand)
				break;

		// Return the corresponding community
		return communities.get(i);
	}

	public static void main(String arg[]) {
		IntegerNode in = new IntegerNode(1, 1);
		in.communityDistribution.put(100, 5);
		in.communityDistribution.put(101, 95);
		in.communityDistribution.put(1010, 95);
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());
		System.out.println(in.speakerVote());

	}

}
