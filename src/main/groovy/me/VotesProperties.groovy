package me

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("votes")
class VotesProperties {
    List<String> candidates
    List<List<Integer>> voters
}
	
