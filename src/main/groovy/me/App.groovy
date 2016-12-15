package me

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

import javax.annotation.PostConstruct

@SpringBootApplication
@EnableConfigurationProperties(VotesProperties.class)
class App {

	@Autowired
	VotesProperties votesProperties

	@PostConstruct
	def postConstruct() {
		
		Integer candidatesNumber = votesProperties.candidates.size()

		def candidateScores = new ArrayList<CandidateScore>(candidatesNumber)

		// candidates
		candidatesNumber.times { int candidate ->

			def candidateScore = new CandidateScore(candidate)

			candidateScores.add(candidateScore)

			// voters
			votesProperties.voters.each { List<Integer> voter ->

				def candidatePosition = voter[candidate]

				candidatesNumber.times { int rival ->

					if (candidate != rival) {

						def rivalPosition = voter[rival]

						if (candidatePosition < rivalPosition) {
							candidateScore.score += 2
						} else if (candidatePosition == rivalPosition) {
							candidateScore.score += 1
						}
					}
				}
			}
		}

		candidateScores.sort(true, {o1, o2 -> -(o1.score <=> o2.score)})

		candidateScores.eachWithIndex {it, int i ->

			String candidateName = votesProperties.candidates[it.candidate]

			println "${i + 1}. ${candidateName}: ${it.score}"
		}
	}
	
	static void main(String[] args) {
		SpringApplication.run App, args
	}
}
