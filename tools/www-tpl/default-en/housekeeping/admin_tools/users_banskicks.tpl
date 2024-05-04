{% include "housekeeping/base/header.tpl" %}
  <body>
    {% set bansActive = " active" %}
	{% include "housekeeping/base/navigation.tpl" %}
	{% include "housekeeping/base/navigation_admin_tools.tpl" %}
     <h2 class="mt-4">Remote banning and kicking</h2>		
		<p>Here can search a user for give a remoting way kick, ban or super ban to a user on the hotel.</p>
		{% include "housekeeping/base/alert.tpl" %}
		<form class="table-responsive col-md-4" method="post">
			<div class="form-group">
				<label for="field">Field</label>
				<select name="searchField" class="form-control" id="field">
					<option value="username">Username</option>
					<option value="id">ID</option>					
				</select>
			</div>
			<div class="form-group" style="display: none;">
				<label for="field">Search type</label>
				<select name="searchType" class="form-control" id="field">					
					<option value="equals">Equals</option>
				</select>
			</div>
			<div class="form-group">
				<label for="searchFor">Search data</label>
				<input type="text" name="searchQuery" class="form-control" id="searchFor" placeholder="Looking for...">
			</div>
			<button type="submit">Perform Search</button>
		</form>
		<br>
		{% if players|length > 0 %}
		<h2 class="mt-4">Search Results</h2>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
				  <th>Look</th>
                  <th>Mission</th>
				  <th>Date joined</th>
				  <th>Last online</th>
				  <th>Action</th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for player in players %}
                <tr>
                  <td>{{ player.id }}</td>
                  <td>{{ player.name }}</td>
				  <td><img src="{{ site.habboImagingPath }}/habbo-imaging/avatarimage?figure={{ player.figure }}&size=s"></td>
                  <td>{{ player.mission }}</td>
				  <td>{{ player.formatJoinDate("dd-MM-yyyy HH:mm:ss") }}</td>
				  <td>{{ player.formatLastOnline("dd-MM-yyyy HH:mm:ss") }}</td>				  
				  <td>
				  <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/users/mod_tool?username={{ player.name }}"><button type="button">Mod Tool User</button></a>
				  <a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/api/superban?username={{ player.name }}"><button type="button">Permanently Ban User</button></a>				  
				  </td>
                </tr>
			   {% set num = num + 1 %}
			   {% endfor %}
              </tbody>
            </table>
          </div>
		{% endif %}			
			<h3 class="mt-4">View bans</h3>
		<p>Manage all currently active bans on the hotel</p>
		<div style="margin:10px">
			{% if nextBans|length > 0 %}
				{% set ourNextPageBan = pageBan + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/bans_kicks?pageBan={{ ourNextPageBan }}&sortBan={{ sortByBan }}"><button type="button">Next Page</button></a>
			{% endif %}
			{% if previousBans|length > 0 %}
				{% set ourNextPageBan = pageBan - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/bans_kicks?pageBan={{ ourNextPageBan }}&sortBan={{ sortByBan }}"><button type="button">Go back</button></a>
			{% endif %}
			</div>
		  <div class="table-responsive">
		    <form method="post">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>Type</th>
				  <th>Value</th>
				  <th>Message</th>
				  <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/bans_kicks?pageBan={{ pageBan }}&sortBan=banned_at">Banned At</a></th>
				  <th><a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/bans_kicks?pageBan={{ pageBan }}&sortBan=banned_until">Banned Util</a></th>				  
				  <th>Moderator</th>
                </tr>
              </thead>
              <tbody>
			      {% for ban in bans %}
				  <tr>
				  <td>
						{% if (ban.getBanType().name() == 'MACHINE_ID') %}
						Machine
						{% endif %}
						{% if (ban.getBanType().name() == 'USER_ID') %}
						User
						{% endif %}
				  </td>
				  <td>
						{% if (ban.getBanType().name() == 'MACHINE_ID') %}
							{% set bannedName = ban.getName() %}
							{{ ban.getValue() }}
							{% if bannedName != "" %}
								&nbsp;-&nbsp;{{ bannedName }}	
							{% endif %}
						{% endif %}
						{% if (ban.getBanType().name() == 'USER_ID') %}
						{% set bannedName = ban.getName() %}
							{% if bannedName != "" %}
								{{ bannedName }}	
							{% endif %}
						{% endif %}
				  </td>
				  <td>
						{{ ban.getMessage() }}
				  </td>
				  <td>
						{{ ban.getBannedAt() }}
				  </td>
				  <td>
						{{ ban.getBannedUtil() }}
				  </td>				  
				  <td>
						{{ ban.getBannedBy() }}
				  </td>
				  </tr>
                  {% endfor %}
              </tbody>
            </table>
		</form>
      </div>
	  <h3 class="mt-4">View kicks</h3>
		  <p>Here can see the most recent logs of Kicks created via RCON.</p>
		  <div style="margin:10px">
			{% if nextremoteKickLogs|length > 0 %}
				{% set ourNextPageKick = pageKick + 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/bans_kicks?pageKick={{ ourNextPageKick }}&sort={{ kickSortBy }}"><button type="button">Next Page</button></a>
			{% endif %}
			{% if previousremoteKickLogs|length > 0 %}
				{% set ourNextPageKick = pageKick - 1 %}
				<a href="{{ site.sitePath }}/{{ site.housekeepingPath }}/admin_tools/bans_kicks?pageKick={{ ourNextPageKick }}&sort={{ kickSortBy }}"><button type="button">Go back</button></a>
			{% endif %}
			</div>
          <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
				  <th>Type</th>                            				  				  				  
				  <th>User</th>	
				  <th>Message</th>				  
				  <th>Kicked at</th>
				  <th>Moderator</th>
                </tr>
              </thead>
              <tbody>
			    {% set num = 1 %}
				{% for remoteKickLog in remoteKickLogs %}
                <tr>
				  <td>{{ remoteKickLog.type }}</td>
				  <td>{{ remoteKickLog.user }}</td>
				  <td>{{ remoteKickLog.message }}</td>				  				  				  	
				  <td>{{ remoteKickLog.timestamp }}</td>
				  <td>{{ remoteKickLog.moderator }}</td>
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