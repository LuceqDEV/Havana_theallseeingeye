{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set configurationsActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
	{% include "housekeeping/base/navigation_system_status.tpl" %}
	<h2 class="mt-4">{% if CategoryExists %}<text style="text-transform: capitalize;">{{ categoryName }}</text>{% else %}Configuration{% endif %} settings</h2>		
		<p>{% if CategoryExists %}These are the all configurations of the {{ categoryName }}.{% else %}<text style="color:red;">Please select a setting in order to edit them.</text>{% endif %}</p>
		 {% if CategoryExists %}
		 {% include "housekeeping/base/alert.tpl" %}
          <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th style="width: 50%;">Name</th>
				  <th>Value</th>
                </tr>
              </thead>
              <tbody>
				{% for settings in configs %}
                <tr>
				  <td>
					<div><b>{{ settings.setting }}</b></div>
					<div>{{ settings.description }}</div>
				  </td>
				  {% if settings.value == 'true' or settings.value == 'false' %}
				  <td>
					<select name="{{ settings.setting }}" id="searchFor" class="form-control">
						<option value="true" {% if settings.value == 'true' %}selected{% endif %}>True (enabled)</option>
						<option value="false" {% if settings.value == 'false' %}selected{% endif %}>False (disabled)</option>
					</select>
				  {% else %}
				  <td>
						<input type="text" name="{{ settings.setting }}" class="form-control" id="searchFor" value="{{ settings.value }}">
				  </td>
				  {% endif %}
                  </tr>
			   {% endfor %}
              </tbody>
            </table>
			<div class="form-group"> 
				<button type="submit">Save Configuration</button>
			</div>
		</form>
      </div>
	  {% endif %}
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