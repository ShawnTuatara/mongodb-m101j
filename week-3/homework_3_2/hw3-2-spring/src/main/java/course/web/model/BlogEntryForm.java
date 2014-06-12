package course.web.model;

import org.hibernate.validator.constraints.NotEmpty;

public class BlogEntryForm {
	@NotEmpty
	private String subject;

	@NotEmpty
	private String body;

	private String tags;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
