#!/bin/bash

# Script de Testing de Performance para API Gateway
# Autor: Performance Testing Team
# Fecha: $(date)

echo "⚡ INICIANDO TESTING DE PERFORMANCE DEL API GATEWAY"
echo "==================================================="

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Marcar tiempo de inicio del script
script_start_time=$(date +%s)

# Variables
GATEWAY_URL="http://localhost:8080"

# Función para imprimir resultados
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ $2${NC}"
    else
        echo -e "${RED}❌ $2${NC}"
    fi
}

# Función para medir tiempo de respuesta
measure_response_time() {
    local url="$1"
    local description="$2"

    echo -e "${BLUE}🔍 Probando: $description${NC}"

    # Medir tiempo de respuesta
    start_time=$(date +%s%N)
    response=$(curl -s -o /dev/null -w "%{http_code}" "$url")
    end_time=$(date +%s%N)

    # Calcular tiempo en milisegundos
    duration=$(( (end_time - start_time) / 1000000 ))

    if [ "$response" = "200" ]; then
        print_result 0 "$description (Status: $response, Tiempo: ${duration}ms)"
    else
        print_result 1 "$description (Status: $response, Tiempo: ${duration}ms)"
    fi

    echo $duration
}

# Función para test de carga
load_test() {
    local url="$1"
    local description="$2"
    local requests="$3"
    local concurrency="$4"

    echo -e "${BLUE}🔍 Probando carga: $description${NC}"
    echo "📊 Configuración: $requests requests, $concurrency conexiones concurrentes"

    # Usar curl para simular carga
    start_time=$(date +%s)

    # Ejecutar requests en paralelo (sin mostrar output individual)
    for i in $(seq 1 $concurrency); do
        (
            for j in $(seq 1 $((requests/concurrency))); do
                curl -s -o /dev/null -w "%{http_code}\n" "$url" > /dev/null 2>&1 &
            done
            wait
        ) &
    done
    wait

    end_time=$(date +%s)
    total_time=$((end_time - start_time))

    if [ $total_time -eq 0 ]; then
        total_time=1  # Evitar división por cero
    fi

    requests_per_second=$((requests/total_time))

    echo -e "${GREEN}✅ Completado: ${requests} requests en ${total_time}s (${requests_per_second} req/s)${NC}"
}

echo -e "\n${YELLOW}1. TESTING DE TIEMPO DE RESPUESTA BÁSICO${NC}"
echo "----------------------------------------"
measure_response_time "$GATEWAY_URL/actuator/health" "Health Check"
measure_response_time "$GATEWAY_URL/api/v1/profiles" "Lista de Perfiles"
measure_response_time "$GATEWAY_URL/api/v1/trips" "Lista de Viajes"
measure_response_time "$GATEWAY_URL/api/v1/requestServices" "Lista de Solicitudes"

echo -e "\n${YELLOW}2. TESTING DE CARGA LIGERA${NC}"
echo "---------------------------"
load_test "$GATEWAY_URL/api/v1/profiles" "Carga ligera en perfiles" 100 10
load_test "$GATEWAY_URL/api/v1/trips" "Carga ligera en viajes" 100 10

echo -e "\n${YELLOW}3. TESTING DE CARGA MEDIA${NC}"
echo "-------------------------"
load_test "$GATEWAY_URL/api/v1/profiles" "Carga media en perfiles" 500 25
load_test "$GATEWAY_URL/api/v1/trips" "Carga media en viajes" 500 25

echo -e "\n${YELLOW}4. TESTING DE CARGA ALTA${NC}"
echo "------------------------"
load_test "$GATEWAY_URL/api/v1/profiles" "Carga alta en perfiles" 1000 50
load_test "$GATEWAY_URL/api/v1/trips" "Carga alta en viajes" 1000 50

echo -e "\n${YELLOW}5. TESTING DE CONCURRENCIA${NC}"
echo "---------------------------"
echo -e "${BLUE}🔍 Probando diferentes niveles de concurrencia...${NC}"

concurrency_levels=(5 10 25 50 100)
for concurrency in "${concurrency_levels[@]}"; do
    echo -e "${BLUE}📊 Probando con $concurrency conexiones concurrentes...${NC}"
    load_test "$GATEWAY_URL/api/v1/profiles" "Concurrencia $concurrency" 200 $concurrency
done

echo -e "\n${YELLOW}6. TESTING DE MEMORIA Y CPU${NC}"
echo "----------------------------"
echo -e "${BLUE}🔍 Verificando uso de recursos...${NC}"

# Obtener métricas de memoria
memory_metrics=$(curl -s "$GATEWAY_URL/actuator/metrics/jvm.memory.used" | grep -o '"value":[0-9]*' | cut -d':' -f2)
if [ -n "$memory_metrics" ]; then
    memory_mb=$((memory_metrics / 1024 / 1024))
    echo "Uso de memoria: ${memory_mb}MB"

    if [ $memory_mb -lt 512 ]; then
        print_result 0 "Uso de memoria aceptable (${memory_mb}MB)"
    else
        print_result 1 "Uso de memoria alto (${memory_mb}MB)"
    fi
else
    print_result 1 "No se pudieron obtener métricas de memoria"
fi

echo -e "\n${YELLOW}7. TESTING DE THROUGHPUT${NC}"
echo "------------------------"
echo -e "${BLUE}🔍 Probando throughput con diferentes tamaños de payload...${NC}"

# Test con diferentes tamaños de requests
for size in 1 10 50 100; do
    echo -e "${BLUE}📊 Probando con $size requests simultáneos...${NC}"
    start_time=$(date +%s%N)

    for i in $(seq 1 $size); do
        curl -s -o /dev/null "$GATEWAY_URL/api/v1/profiles" > /dev/null 2>&1 &
    done
    wait

    end_time=$(date +%s%N)
    duration=$(( (end_time - start_time) / 1000000 ))

    if [ $duration -eq 0 ]; then
        duration=1  # Evitar división por cero
    fi

    throughput=$(( (size * 1000) / duration ))

    echo -e "${GREEN}✅ Throughput: ${throughput} requests/segundo${NC}"
done

echo -e "\n${YELLOW}8. TESTING DE LATENCIA${NC}"
echo "----------------------"
echo -e "${BLUE}🔍 Probando latencia con múltiples requests...${NC}"

latencies=()
echo -e "${BLUE}📊 Probando latencia con 10 requests...${NC}"
for i in {1..10}; do
    start_time=$(date +%s%N)
    curl -s -o /dev/null "$GATEWAY_URL/api/v1/profiles" > /dev/null 2>&1
    end_time=$(date +%s%N)
    latency=$(( (end_time - start_time) / 1000000 ))
    latencies+=($latency)
done

# Calcular estadísticas de latencia
total=0
for latency in "${latencies[@]}"; do
    total=$((total + latency))
done
avg_latency=$((total / ${#latencies[@]}))

echo "Latencia promedio: ${avg_latency}ms"

echo -e "\n${YELLOW}9. TESTING DE STRESS${NC}"
echo "-------------------"
echo -e "${BLUE}🔍 Ejecutando test de stress...${NC}"

# Test de stress por 30 segundos
echo -e "${BLUE}📊 Ejecutando test de stress por 30 segundos...${NC}"
start_time=$(date +%s)
requests=0
errors=0

while [ $(($(date +%s) - start_time)) -lt 30 ]; do
    response=$(curl -s -o /dev/null -w "%{http_code}" "$GATEWAY_URL/api/v1/profiles" 2>/dev/null)
    requests=$((requests + 1))

    if [ "$response" != "200" ]; then
        errors=$((errors + 1))
    fi
done

if [ $requests -eq 0 ]; then
    requests=1  # Evitar división por cero
fi

error_rate=$(( (errors * 100) / requests ))

echo -e "${GREEN}✅ Stress test completado:${NC}"
echo "   📊 Requests totales: $requests"
echo "   ❌ Errores: $errors"
echo "   📈 Tasa de error: ${error_rate}%"

echo -e "\n${YELLOW}10. TESTING DE RECUPERACIÓN${NC}"
echo "---------------------------"
echo -e "${BLUE}🔍 Probando recuperación después de carga alta...${NC}"

# Hacer carga alta
load_test "$GATEWAY_URL/api/v1/profiles" "Carga alta para test de recuperación" 500 50

# Esperar 5 segundos
echo -e "${BLUE}⏳ Esperando 5 segundos para recuperación...${NC}"
sleep 5

# Verificar recuperación
measure_response_time "$GATEWAY_URL/api/v1/profiles" "Recuperación después de carga"

echo -e "\n${GREEN}🎉 TESTING DE PERFORMANCE DEL API GATEWAY COMPLETADO${NC}"
echo "======================================================="

# Resumen final
echo -e "\n${BLUE}📊 RESUMEN DE PERFORMANCE:${NC}"
echo "======================================================="
echo "📅 Fecha de ejecución: $(date)"
echo "🌐 API Gateway URL: $GATEWAY_URL"
echo "⏱️  Duración total del test: $(($(date +%s) - script_start_time))s"
echo "======================================================="