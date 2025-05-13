@REM helm install dynatrace-operator oci://public.ecr.aws/dynatrace/dynatrace-operator --version 1.5.1 --create-namespace --namespace dynatrace --atomic

helm install dynatrace-operator oci://public.ecr.aws/dynatrace/dynatrace-operator --create-namespace --namespace dynatrace --atomic



@REM helm install dynatrace-operator oci://public.ecr.aws/dynatrace/dynatrace-operator --atomic
@REM kubectl apply -f ./dynatraceNS.yaml


@REM  kubectl label --overwrite ns dynatrace pod-security.io/enforce=privileged
@REM  kubectl label --overwrite ns dynatrace pod-security.io/enforce-version=latest
@REM  kubectl label --overwrite ns dynatrace pod-security.io/audit=privileged
@REM  kubectl label --overwrite ns dynatrace pod-security.io/audit-version=latest
@REM  kubectl label --overwrite ns dynatrace pod-security.io/warn=privileged
@REM  kubectl label --overwrite ns dynatrace pod-security.io/warn-version=latest


kubectl apply -f ./dynakube_app_only.yaml
