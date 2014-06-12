<!doctype HTML>
<html
<head>
    <title>
        Blog Post
    </title>
</head>
<body>
<#if username??>
    Welcome ${username} <a href="/logout">Logout</a> | <a href="/newpost">New Post</a>

    <p>
</#if>

<a href="/">Blog Home</a><br><br>

<h2>${blogEntry["title"]}</h2>
Posted ${blogEntry["postDate"]?datetime}<i> By ${blogEntry["author"]}</i><br>
<hr>
${blogEntry["body"]}
<p>
    <em>Filed Under</em>:
    <#if blogEntry["tags"]??>
        <#list blogEntry["tags"] as tag>
            ${tag}
        </#list>
    </#if>
<p>
    Comments:
<ul>
    <#if blogEntry["comments"]??>
        <#assign numComments = blogEntry["comments"]?size>
            <#else>
                <#assign numComments = 0>
    </#if>
    <#if (numComments > 0)>
        <#list 0 .. (numComments -1) as i>

                Author: ${blogEntry["comments"][i]["author"]}<br>
            <br>
            ${blogEntry["comments"][i]["body"]}<br>
            <hr>
        </#list>
    </#if>
    <h3>Add a comment</h3>

    <form action="/newcomment" method="POST">
        <input type="hidden" name="permalink", value="${blogEntry["permalink"]}">
        ${errors!""}<br>
        <b>Name</b> (required)<br>
        <input type="text" name="commentName" size="60" value=""><br>
        <b>Email</b> (optional)<br>
        <input type="text" name="commentEmail" size="60" value=""><br>
        <b>Comment</b><br>
        <textarea name="commentBody" cols="60" rows="10"></textarea><br>
        <input type="submit" value="Submit">
    </form>
</ul>
</body>
</html>


