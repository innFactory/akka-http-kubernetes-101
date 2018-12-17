#!/bin/bash

rp generate-kubernetes-resources "eu.gcr.io/innfactory-k8s101/k8sdemo:latest" \
  --generate-all \
  --ingress-name k8sdemo \
  --service-type LoadBalancer \
  --version 1.0 \
  --pod-controller-replicas 3 | kubectl apply -f -