#!/bin/bash

# Script de Diagnóstico para API Gateway
# Autor: Testing Team
# Fecha: $(date)

echo "🔍 DIAGNÓSTICO DEL API GATEWAY"
echo "================================"

# Variables
GATEWAY_URL="http://localhost:8080"
EUREKA_URL="http://localhost:8761"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "\n${YELLOW}1. VERIFICANDO ESTADO DE LOS SERVICIOS${NC}"
echo "----------------------------------------"

# Verificar si el API Gateway está corriendo
echo -e "${BLUE}🔍 Verificando API Gateway...${NC}"
gateway_status=$(curl -s -o /dev/null -w "%{http_code}" "$GATEWAY_URL")
if [ "$gateway_status" = "200" ] || [ "$gateway_status" = "401" ] || [ "$gateway_status" = "404" ]; then
    echo -e "${GREEN}✅ API Gateway está respondiendo (Status: $gateway_status)${NC}"
else
    echo -e "${RED}❌ API Gateway no está respondiendo (Status: $gateway_status)${NC}"
fi

# Verificar Eureka
echo -e "${BLUE}🔍 Verificando Eureka...${NC}"
eureka_status=$(curl -s -o /dev/null -w "%{http_code}" "$EUREKA_URL")
if [ "$eureka_status" = "200" ]; then
    echo -e "${GREEN}✅ Eureka está respondiendo${NC}"
else
    echo -e "${RED}❌ Eureka no está respondiendo (Status: $eureka_status)${NC}"
fi

echo -e "\n${YELLOW}2. VERIFICANDO RUTAS DE ACTUATOR${NC}"
echo "----------------------------------------"

# Probar rutas de actuator con curl completo
echo -e "${BLUE}🔍 Probando /actuator/health...${NC}"
health_response=$(curl -s "$GATEWAY_URL/actuator/health")
echo "Response: $health_response"

echo -e "${BLUE}🔍 Probando /actuator/info...${NC}"
info_response=$(curl -s "$GATEWAY_URL/actuator/info")
echo "Response: $info_response"

echo -e "${BLUE}🔍 Probando /actuator/metrics...${NC}"
metrics_response=$(curl -s "$GATEWAY_URL/actuator/metrics")
echo "Response: $metrics_response"

echo -e "\n${YELLOW}3. VERIFICANDO CONFIGURACIÓN CORS${NC}"
echo "----------------------------------------"

# Probar CORS con headers detallados
echo -e "${BLUE}🔍 Probando CORS con OPTIONS...${NC}"
cors_response=$(curl -s -I -X OPTIONS "$GATEWAY_URL/api/v1/users" \
    -H "Origin: http://localhost:3000" \
    -H "Access-Control-Request-Method: GET" \
    -H "Access-Control-Request-Headers: Content-Type")
echo "CORS Response Headers:"
echo "$cors_response"

echo -e "\n${YELLOW}4. VERIFICANDO RUTAS DE SERVICIOS${NC}"
echo "----------------------------------------"

# Probar rutas de servicios con más detalle
services=(
    "api/v1/profiles"
    "api/v1/companie/1"
    "api/v1/paymentcards/1"
    "api/v1/requestServices"
    "api/v1/drivers"
    "api/v1/trips"
    "api/v1/vehicles"
    "api/v1/alert"
    "api/v1/expense"
    "api/v1/evidence"
)

for service in "${services[@]}"; do
    echo -e "${BLUE}🔍 Probando $service...${NC}"
    response=$(curl -s -o /dev/null -w "%{http_code}" "$GATEWAY_URL/$service")
    echo "Status: $response"
done

echo -e "\n${YELLOW}5. VERIFICANDO DOCUMENTACIÓN SWAGGER${NC}"
echo "----------------------------------------"

# Probar documentación Swagger
swagger_endpoints=(
    "iam/v3/api-docs"
    "profile/v3/api-docs"
    "companies/v3/api-docs"
    "paymentcards/v3/api-docs"
    "request/v3/api-docs"
    "trips/v3/api-docs"
)

for endpoint in "${swagger_endpoints[@]}"; do
    echo -e "${BLUE}🔍 Probando $endpoint...${NC}"
    response=$(curl -s -o /dev/null -w "%{http_code}" "$GATEWAY_URL/$endpoint")
    echo "Status: $response"
done

echo -e "\n${YELLOW}6. VERIFICANDO SWAGGER UI${NC}"
echo "----------------------------------------"

echo -e "${BLUE}🔍 Probando Swagger UI...${NC}"
swagger_ui_response=$(curl -s -o /dev/null -w "%{http_code}" "$GATEWAY_URL/swagger-ui.html")
echo "Swagger UI Status: $swagger_ui_response"

echo -e "\n${YELLOW}7. VERIFICANDO HEADERS DE RESPUESTA${NC}"
echo "----------------------------------------"

echo -e "${BLUE}🔍 Verificando headers de una ruta protegida...${NC}"
headers=$(curl -s -I "$GATEWAY_URL/api/v1/profiles")
echo "Headers:"
echo "$headers"

echo -e "\n${YELLOW}8. VERIFICANDO LOGS DEL API GATEWAY${NC}"
echo "----------------------------------------"

echo -e "${BLUE}🔍 Los logs del API Gateway deberían mostrar:${NC}"
echo "- Authorization header received: null (para requests sin token)"
echo "- Errores de CORS si los hay"
echo "- Errores de routing si los hay"

echo -e "\n${GREEN}🎉 DIAGNÓSTICO COMPLETADO${NC}"
echo "=========================================="

echo -e "\n${BLUE}📋 RECOMENDACIONES:${NC}"
echo "1. Verificar que todos los microservicios estén corriendo"
echo "2. Verificar que Eureka esté registrando todos los servicios"
echo "3. Verificar la configuración CORS en GatewaySecurityConfig"
echo "4. Verificar las rutas en application.properties"
echo "5. Verificar que los servicios tengan endpoints de health check" 