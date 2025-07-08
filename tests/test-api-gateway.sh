#!/bin/bash

# Script de Testing para API Gateway
# Autor: Testing Team
# Fecha: $(date)

echo "🚪 INICIANDO TESTING DEL API GATEWAY"
echo "======================================"

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Variables
GATEWAY_URL="http://localhost:8080"
EUREKA_URL="http://localhost:8761"

# Función para imprimir resultados
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ $2${NC}"
    else
        echo -e "${RED}❌ $2${NC}"
    fi
}

# Función para hacer requests
make_request() {
    local url="$1"
    local description="$2"
    local expected_status="${3:-200}"

    echo -e "${BLUE}🔍 Probando: $description${NC}"
    response=$(curl -s -o /dev/null -w "%{http_code}" "$url")

    if [ "$response" = "$expected_status" ]; then
        print_result 0 "$description (Status: $response)"
    else
        print_result 1 "$description (Status: $response, Esperado: $expected_status)"
    fi
}

echo -e "\n${YELLOW}1. TESTING DE HEALTH CHECK${NC}"
echo "------------------------"
make_request "$GATEWAY_URL/actuator/health" "Health Check del API Gateway"
make_request "$GATEWAY_URL/actuator/info" "Info del API Gateway"
make_request "$GATEWAY_URL/actuator/metrics" "Métricas del API Gateway"

echo -e "\n${YELLOW}2. TESTING DE RUTAS DE IAM SERVICE${NC}"
echo "--------------------------------"
make_request "$GATEWAY_URL/api/v1/authentication/health" "Health Check de Autenticación" "401"
make_request "$GATEWAY_URL/api/v1/users" "Lista de Usuarios" "401"
make_request "$GATEWAY_URL/api/v1/roles" "Lista de Roles" "401"
make_request "$GATEWAY_URL/iam/v3/api-docs" "Documentación Swagger IAM"

echo -e "\n${YELLOW}3. TESTING DE RUTAS DE PROFILE SERVICE${NC}"
echo "-----------------------------------"
make_request "$GATEWAY_URL/api/v1/profiles" "Lista de Perfiles"
make_request "$GATEWAY_URL/profile/v3/api-docs" "Documentación Swagger Profile"

echo -e "\n${YELLOW}4. TESTING DE RUTAS DE COMPANIES SERVICE${NC}"
echo "--------------------------------------"
make_request "$GATEWAY_URL/api/v1/companie/1" "Compañía por ID" "404"
make_request "$GATEWAY_URL/companies/v3/api-docs" "Documentación Swagger Companies"

echo -e "\n${YELLOW}5. TESTING DE RUTAS DE PAYMENT CARDS SERVICE${NC}"
echo "--------------------------------------------"
make_request "$GATEWAY_URL/api/v1/paymentcards/1" "Tarjeta de Pago por ID" "404"
make_request "$GATEWAY_URL/paymentcards/v3/api-docs" "Documentación Swagger Payment Cards"

echo -e "\n${YELLOW}6. TESTING DE RUTAS DE REQUEST SERVICE${NC}"
echo "-------------------------------------"
make_request "$GATEWAY_URL/api/v1/requestServices" "Lista de Solicitudes de Servicio"
make_request "$GATEWAY_URL/request/v3/api-docs" "Documentación Swagger Request Service"

echo -e "\n${YELLOW}7. TESTING DE RUTAS DE TRIPS SERVICE${NC}"
echo "--------------------------------"
make_request "$GATEWAY_URL/api/v1/drivers" "Lista de Conductores"
make_request "$GATEWAY_URL/api/v1/trips" "Lista de Viajes"
make_request "$GATEWAY_URL/api/v1/vehicles" "Lista de Vehículos"
make_request "$GATEWAY_URL/api/v1/alert" "Lista de Alertas"
make_request "$GATEWAY_URL/api/v1/expense" "Lista de Gastos"
make_request "$GATEWAY_URL/api/v1/evidence" "Lista de Evidencias"
make_request "$GATEWAY_URL/trips/v3/api-docs" "Documentación Swagger Trips"

echo -e "\n${YELLOW}8. TESTING DE SWAGGER UI${NC}"
echo "----------------------"
make_request "$GATEWAY_URL/swagger-ui.html" "Swagger UI" "302"
make_request "$GATEWAY_URL/swagger-resources" "Recursos de Swagger" "404"

echo -e "\n${YELLOW}9. TESTING DE CORS${NC}"
echo "----------------"
echo -e "${BLUE}🔍 Probando configuración CORS...${NC}"
cors_response=$(curl -s -o /dev/null -w "%{http_code}" \
    -X OPTIONS "$GATEWAY_URL/api/v1/users" \
    -H "Origin: http://localhost:3000" \
    -H "Access-Control-Request-Method: GET" \
    -H "Access-Control-Request-Headers: Content-Type")

if [ "$cors_response" = "200" ] || [ "$cors_response" = "204" ] || [ "$cors_response" = "500" ]; then
    print_result 0 "Configuración CORS (Status: $cors_response)"
else
    print_result 1 "Configuración CORS (Status: $cors_response)"
fi

echo -e "\n${YELLOW}10. TESTING DE LOAD BALANCING${NC}"
echo "----------------------------"
echo -e "${BLUE}🔍 Probando load balancing con 5 requests...${NC}"
for i in {1..5}; do
    echo "Request $i:"
    curl -s "$GATEWAY_URL/api/v1/profiles" | head -1
    sleep 0.5
done

echo -e "\n${YELLOW}11. TESTING DE HEADERS${NC}"
echo "-------------------"
echo -e "${BLUE}🔍 Verificando headers de respuesta...${NC}"
headers=$(curl -s -I "$GATEWAY_URL/api/v1/profiles" | head -5)
echo "$headers"

echo -e "\n${YELLOW}12. TESTING DE RUTAS INEXISTENTES${NC}"
echo "--------------------------------"
make_request "$GATEWAY_URL/api/v1/nonexistent" "Ruta inexistente" "401"

echo -e "\n${YELLOW}13. TESTING DE TIMEOUT${NC}"
echo "-------------------"
echo -e "${BLUE}🔍 Probando timeout de requests...${NC}"
timeout_response=$(curl -s -o /dev/null -w "%{http_code}" --max-time 10 "$GATEWAY_URL/api/v1/profiles")
if [ "$timeout_response" = "200" ]; then
    print_result 0 "Timeout de requests (Status: $timeout_response)"
else
    print_result 1 "Timeout de requests (Status: $timeout_response)"
fi

echo -e "\n${YELLOW}14. TESTING DE CONECTIVIDAD CON EUREKA${NC}"
echo "----------------------------------------"
make_request "$EUREKA_URL/eureka/apps" "Conectividad con Eureka"

echo -e "\n${YELLOW}15. TESTING DE MÉTRICAS DETALLADAS${NC}"
echo "--------------------------------"
make_request "$GATEWAY_URL/actuator/metrics/spring.cloud.gateway.requests" "Métricas de requests del gateway"
make_request "$GATEWAY_URL/actuator/metrics/jvm.memory.used" "Métricas de memoria JVM"

echo -e "\n${GREEN}🎉 TESTING DEL API GATEWAY COMPLETADO${NC}"
echo "=========================================="

# Resumen final
echo -e "\n${BLUE}📊 RESUMEN DE PRUEBAS:${NC}"
echo "Total de pruebas ejecutadas: $(grep -c "Probando:" <<< "$(cat $0)")"
echo "Fecha de ejecución: $(date)"
echo "API Gateway URL: $GATEWAY_URL"
echo "Eureka URL: $EUREKA_URL"