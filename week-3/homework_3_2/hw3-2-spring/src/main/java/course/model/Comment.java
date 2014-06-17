package course.model;

public class Comment {
	private String author;

	private String email;

	private String body;

	public Comment(String author, String email, String body) {
		this.author = author;
		this.email = email;
		this.body = body;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}