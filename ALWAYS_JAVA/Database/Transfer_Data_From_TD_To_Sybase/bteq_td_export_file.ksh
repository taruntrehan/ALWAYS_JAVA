# Note : Please change the connection  parameters as per your requirement.
bteq >> /home/allzhere/TD_Extract.log <<EOF
		.logmech 'ldap'
        .logon 'TD_Server_IP_Address'/'user_name','password'
        .set width 10000;
        .set separator '|';
        .export report file = /home/allzhere/TD_Extract.out;
select * from person_dtls;
.export reset
.quit
EOF