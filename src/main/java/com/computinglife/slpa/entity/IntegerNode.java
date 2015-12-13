package com.computinglife.slpa.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.computinglife.slpa.util.RandomNumGenerator;

public class IntegerNode {
	public int id;

	public int community;
	// 存放该点所属的标签s，key为标签id,value为该标签在改点上所出现的次数
	public HashMap<Integer, Integer> communityDistribution;

	public IntegerNode(int id, int community) {
		this.id = id;
		this.community = community;
		this.communityDistribution = new HashMap<Integer, Integer>();
	}

	public void updateCommunityDistribution(int votedCommunity, int voteIncrement) {

		if (communityDistribution.containsKey(votedCommunity))
			voteIncrement += communityDistribution.get(votedCommunity);

		communityDistribution.put(votedCommunity, voteIncrement);

	}

	// speak rule，随机从标签s中选择一个
	// FIXME:这种随机方式是否能有所改进?
	public int speakerVote() {

		Set<Integer> communityIds = communityDistribution.keySet();
		ArrayList<Integer> communities = new ArrayList<Integer>();
		ArrayList<Integer> cumulativeCounts = new ArrayList<Integer>();

		int sum = -1;
		for (Integer comm : communityIds) {
			sum += communityDistribution.get(comm);
			communities.add(comm);
			cumulativeCounts.add(sum);
		}

		int rand = RandomNumGenerator.getRandomInt(sum + 1);

		int i = 0;
		for (i = 0; i < cumulativeCounts.size(); i++)
			if (cumulativeCounts.get(i) >= rand)
				break;

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
