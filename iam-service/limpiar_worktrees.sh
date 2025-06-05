#!/bin/bash

echo "Eliminando worktrees fuera de tb3..."

git worktree list | grep -v 'tb3' | awk '{print $1}' | while read dir; do
  echo "Eliminando $dir"
  git worktree remove --force "$dir"
done

echo "¡Limpieza completada!"
