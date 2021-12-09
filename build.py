#!/usr/bin/env python3
args = [
    # Remove target/ dir
    "clean",
    # Compile java sources
    "compiler:compile",
    # Copy resources from src/main/resources to target/classes
    "resources:copy-resources",
    # Zip jar including resources, .class and dependencies
    "assembly:single"
]

if __name__ == '__main__':
    print(f"Executing Maven with args: {args}")
    args.insert(0, "mvn")

    from subprocess import run

    run(args)
