import ast

class IfVisitor(ast.NodeVisitor):
    def __init__(self):
        self.count = 0

    def visit_If(self, node):
        self.count += 1
        self.generic_visit(node)