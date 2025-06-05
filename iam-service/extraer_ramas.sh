#!/bin/bash

# Crear la carpeta tb3 si no existe
mkdir -p ../tb3

# Lista todas las ramas remotas (excepto HEAD)
branches=$(git branch -r | grep -v '\->' | sed 's/origin\///')

# Procesa cada rama
for branch in $branches; do
    echo "Procesando rama: $branch"
    git worktree add ../tb3/$branch $branch
done

echo "¡Listo! Cada rama está en una subcarpeta dentro de tb3."
