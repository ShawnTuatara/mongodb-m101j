package course.web.model;

import javax.validation.constraints.Size;

public class CommentForm {
	private String permalink;

	@Size(min = 1)
	private String commentName;

	private String commentEmail;

	@Size(min = 1)
	private String commentBody;

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

	public String getCommentEmail() {
		return commentEmail;
	}

	public void setCommentEmail(String commentEmail) {
		this.commentEmail = commentEmail;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}
}
