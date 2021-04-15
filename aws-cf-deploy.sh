aws cloudformation deploy --region us-east-2 --stack-name UtopiaOrchestratorMS \
--template-file utopia-cftemplate --parameter-overrides ApplicationName=UtopiaOrchestratorMS \
ECRepositoryUri=$AWS_ID/utopia-orchestrator:$COMMIT_HASH DBUrl=$DB_URL DBUsername=$DB_USERNAME \
DBPassword=$DB_PASSWORD ECSCluster=$UTOPIA_CLUSTER ExecutionRoleArn=$EXECUTION_ROLE_ARN \
SubnetID=$UTOPIA_PRIVATE_SUBNET_1 TargetGroupArnDev=$TARGETGROUP_UTOPIA_ORCHESTRATOR_DEV_ARN \
TargetGroupArnProd=$TARGETGROUP_UTOPIA_ORCHESTRATOR_PROD_ARN VpcId=$UTOPIA_PUBLIC_VPC_ID \
--capabilities "CAPABILITY_IAM" "CAPABILITY_NAMED_IAM"