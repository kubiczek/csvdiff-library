package pl.kubiczek.csvdiff

abstract sealed class ColumnType
case object String extends ColumnType
case object Integer extends ColumnType
case object Long extends ColumnType
case object Double extends ColumnType