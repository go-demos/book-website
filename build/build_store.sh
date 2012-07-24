#!/bin/bash

store=target/book-store
website=$store/website
inventory=$store/inventory
management=$store/management

mkdir -p $website $inventory $management

tar -xvzf book-store.tar.gz book-store