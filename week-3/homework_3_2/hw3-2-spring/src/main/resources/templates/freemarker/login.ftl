<!DOCTYPE html>

<#import "/spring.ftl" as spring />
<html>
  <head>
    <title>Login</title>
    <style type="text/css">
      .label {text-align: right}
      .error {color: red}
    </style>

  </head>

  <body>
    Need to Create an account? <a href="/signup">Signup</a><p>
    <h2>Login</h2>
    <form method="post">
      <table>
        <tr>
          <td class="label">
            Username
          </td>
          <td>
            <input type="text" name="username" value="${loginForm.username!""}">
          </td>
          <td class="error">
          </td>
        </tr>

        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="">
          </td>
          <td class="error">
            <@spring.bind "loginForm" />
        <#list spring.status.errorMessages as error>${error}</#list>
          </td>
        </tr>

      </table>

      <input type="submit">
    </form>
  </body>

</html>
