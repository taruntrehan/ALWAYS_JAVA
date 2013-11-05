CREATE PROCEDURE syb_load_td_exp (IN in_pathname  varchar(255) default NULL,IN in_filename varchar(255)  default NULL)
BEGIN
execute immediate with quotes

'LOAD TABLE td_extract
(
id,
name,
age
) 

FROM ''' || in_pathname || '/' ||  in_filename || '''' || 
'QUOTES OFF  ESCAPES OFF  delimited by  ''|''  '; 

END;
--exec syb_load_td_exp '/home/allzhere','TD_Extract.out'