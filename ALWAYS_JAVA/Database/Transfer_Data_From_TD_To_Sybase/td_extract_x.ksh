# This script will :
# 1. Read TD Extract.
# 2. Strip of headers.
# 3. Append delimiter at the end of each record.
# 4. Remove new line character


export TD_OUT_WC=$(wc -l /home/allzhere/TD_Extract.out | awk '{print $1}' | tr -s " " | sed 's/^[ ]//g')
echo "Record Count in TD Export is-->""$TD_OUT_WC"
if [ $TD_OUT_WC -gt 0 ]
	then
	echo "TD Record Count is Greater than 0"
	export EXT_WC=`expr ${TD_OUT_WC} - 2`
	tail -$EXT_WC /home/allzhere/TD_Extract.out | awk -F"|" '{print $1"|"$2"|"$3"|"}' > /home/allzhere/TD_Extract.tmp
	cat /home/allzhere/TD_Extract.tmp | tr -d '\n' > /home/allzhere/TD_Extract.ext
fi
