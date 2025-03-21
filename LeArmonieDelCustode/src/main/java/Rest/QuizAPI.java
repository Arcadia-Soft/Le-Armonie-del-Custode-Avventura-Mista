package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import tipi.Dialogo;

public class QuizAPI {

	private class ResponseAPI {
		private float response_code;
		List<Results> results = new ArrayList<>();

		public float getResponse_code() {
			return response_code;
		}

		public List<Results> getResults() {
			return results;
		}
	}

	public class Results {
		private String type;
		private String difficulty;
		private String category;
		private String question;
		private String correct_answer;
		private List<String> incorrect_answers;

		// Getter Methods
		public List<String> getIncorrect_answers() {
			return incorrect_answers;
		}

		public void setIncorrect_answers(List<String> incorrect_answers) {
			this.incorrect_answers = incorrect_answers;
		}

		public String getType() {
			return type;
		}

		public String getDifficulty() {
			return difficulty;
		}

		public String getCategory() {
			return category;
		}

		public String getQuestion() {
			return question;
		}

		public String getCorrect_answer() {
			return correct_answer;
		}

		// Setter Methods
		public void setType(String type) {
			this.type = type;
		}

		public void setDifficulty(String difficulty) {
			this.difficulty = difficulty;
		}

		public void setCategory(String category) {
			this.category = category;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public void setCorrect_answer(String correct_answer) {
			this.correct_answer = correct_answer;
		}
	}

	private static ResponseAPI getResponseAPI() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://opentdb.com/api.php");
		Response resp = target.queryParam("amount", 1)
				.queryParam("category", 18)
				.queryParam("type", "multiple")
				.request(MediaType.APPLICATION_JSON).get();
		String jsonString = resp.readEntity(String.class);
		Gson gson = new Gson();
		return gson.fromJson(jsonString, ResponseAPI.class);
	}

	public static Dialogo getQuiz(Dialogo dialogo) {
		ResponseAPI r;
		int maxRequest = 5;
		int totalRequest = 0;

		do {
			r = getResponseAPI();
			if (r.getResponse_code() == 0) {
				/*
				 * Formatter
				 * &quot; -> "
				 * &#039; -> '
				 */
				String s = "\"What's up lil' kid?? Come stai? Mr. Scaramboola qui, come here and PLAY... HIP HIP ARRAY! Dimmi un po'...\""
						+ "\n"
						+ r.getResults().get(0).getQuestion().replaceAll("&quot;", "\"").replaceAll("&#039;", "'");
				s += "\n";
				List<String> allAnswer = new ArrayList<>();
				allAnswer.add(
						r.getResults().get(0).getCorrect_answer().replaceAll("&quot;", "\"").replaceAll("&#039;", "'"));

				for (String answer : r.getResults().get(0).getIncorrect_answers()) {
					allAnswer.add(answer.replaceAll("&quot;", "\"").replaceAll("&#039;", "'"));
				}

				allAnswer.sort((a, b) -> a.length() - b.length());
				for (String answer : allAnswer) {
					s += "\t" + answer + "\n";
				}
				dialogo.setTestoDialogo(s);
				dialogo.setRisposta(r.getResults().get(0).getCorrect_answer());
				System.out.println(r.getResults().get(0).getCorrect_answer()); // DEBUG LINE

			} else {
				totalRequest++;
			}
		} while (r.getResponse_code() != 0 && totalRequest < maxRequest);

		return dialogo;
	}
}