<!DOCTYPE html>

<#import "/spring.ftl" as spring />
<html>
  <head>
    <title>Sign Up</title>
    <style type="text/css">
      .label {text-align: right}
      .error {color: red}
    </style>

  </head>

  <body>
    Already a user? <a href="/login">Login</a><p>
    <h2>Signup</h2>
    <form method="post">
      <table>
        <tr>
          <td class="label">
            Username
          </td>
          <td>
            <input type="text" name="username" value="${signupForm.username!""}">
          </td>
          <td class="error">
          <@spring.bind "signupForm.username" />
        <#list spring.status.errorMessages as error>${error}</#list>
            
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
          <@spring.bind "signupForm.password" />
        <#list spring.status.errorMessages as error>${error}</#list>
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Verify Password
          </td>
          <td>
            <input type="password" name="verify" value="">
          </td>
          <td class="error">
          <@spring.bind "signupForm.verify" />
        <#list spring.status.errorMessages as error>${error}</#list>
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Email (optional)
          </td>
          <td>
            <input type="text" name="email" value="${signupForm.email!""}">
          </td>
          <td class="error">
          <@spring.bind "signupForm.email" />
        <#list spring.status.errorMessages as error>${error}</#list>
            
          </td>
        </tr>
      </table>

      <input type="submit">
    </form>
  </body>

</html>
