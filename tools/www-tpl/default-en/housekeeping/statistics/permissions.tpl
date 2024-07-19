{% include "housekeeping/base/header.tpl" %}
  <body>
	{% set dashboardActive = " active " %}
	{% include "housekeeping/base/navigation.tpl" %}
	{% include "housekeeping/base/navigation_statistics.tpl" %}
		<h2 class="mt-4">Suspicius Staff logs</h2>
		  <p>The recently suspicius permissions Staff logs list is seen below.</p>
		  {% include "housekeeping/base/alert.tpl" %}
			<div class="pagination-buttons-box">
			{% if nextPermissionLogs|length > 0 %}
				{% set ourNextPage = page + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/statistics/permissions?page={{ ourNextPage }}"><button type="button">Next Page</button></a>
			{% endif %}
			{% if previousPermissionLogs|length > 0 %}
				{% set ourNextPage = page - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/statistics/permissions?page={{ ourNextPage }}"><button type="button">Go back</button></a>
			{% endif %}
			</div>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>             				  				  				  
				  <th>User</th>
				  <th>Description</th>
				  <th>Date</th>				  
				  <th>User IP</th>				  
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for permissionLog in PermissionLogs %}
                <tr>
				  <td>{{ permissionLog.id }}</td>
				  <td>{{ permissionLog.userName }} (id: {{ permissionLog.userId }})</td>
				  <td>Try to access to {{ permissionLog.description }}</td>
				  <td>{{ permissionLog.date }}</td>
				  <td>{{ permissionLog.userIp }}</td>		 
                </tr>
			   {% set num = num + 1 %}
			   {% endfor %}
              </tbody>
            </table>
      </div>
    </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"></script>
  <script src="https://blackrockdigital.github.io/startbootstrap-simple-sidebar/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>
</body>
</html>