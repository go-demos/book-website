status=`cat deploy_status.txt`
if [[ "$status" == "Pass"  ]]; then
	exit 0
else
	exit 1
fi
