import ast

class ClassVisitor(ast.NodeVisitor):
    def __init__(self):
        self.count = 0

    def visit_ClassDef(self, node):
        self.count += 1
        self.generic_visit(node)